package com.syx.springboot.threespringboot.CP.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.syx.springboot.threespringboot.CP.Json;
import com.syx.springboot.threespringboot.middle.Middle;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ConsumerRunnable implements Runnable {

    /**
     * 消费队列
     */
    private final BlockingQueue<Middle> queue;

    private final CountDownLatch countDownLatch;


    /**
     * 批处理大小
     */
    private int batchSize;

    /***
     * 每次提交等待的时间间隔，单位ms
     */
    private int batchPutTimeLimit;

    public ConsumerRunnable( BlockingQueue<Middle> queue, CountDownLatch countDownLatch) {
        this.queue = queue;
        this.countDownLatch = countDownLatch;
        this.batchPutTimeLimit = 300;
        this.batchSize = 5;

    }

    @Override
    public void run() {
        try {
            log.debug("consumer thread:{} has started take point from queue", Thread.currentThread().getName());

            boolean readyClose = false;
            //从队列中获取数据等待最大时长
            int waitTimeLimit = batchPutTimeLimit / 3;
            Middle middle = null;
            while (!readyClose) {
                long t0 = System.currentTimeMillis();
                //批处理数据点集合
                List<Middle> middleList = new ArrayList<>(batchSize);
                if (middle != null) {
                    middleList.add(middle);
                    middle = null;
                }

                for (int i = middleList.size(); i < batchSize; i++) {
                    try {

                        Middle point = queue.poll(waitTimeLimit, TimeUnit.MILLISECONDS);
                        if (point != null) {
                            middleList.add(point);
                        }

                        //避免无限循环poll
                        long t1 = System.currentTimeMillis();
                        if (t1 - t0 > batchPutTimeLimit) {
                            break;
                        }
                    } catch (InterruptedException e) {
                        //结束线程
                        readyClose = true;
                        log.info("The consumer thread {} is interrupted", Thread.currentThread().getName());
                        break;
                    }
                }
                //当未获取到队列中数据时，阻塞直到获取到队列的头部元素
                if (middleList.size() == 0 && !readyClose) {
                    try {
                        middle = queue.take();
                    } catch (InterruptedException e) {
                        //结束线程
                        readyClose = true;
                        log.info("The consumer thread {} is interrupted", Thread.currentThread().getName());
                    }
                    continue;
                }
                if (middleList.size() == 0) {
                    continue;
                }
                sendData(middleList);
            }
        } finally {
            this.countDownLatch.countDown();
        }
    }


    /**
     * 发送Http请求写入数据
     *
     * @param middles 数据点集合
     */
    private void sendData(List<Middle> middles) {
        try {
            log.info("|------------thread {} 消费数据:"+middles.size()+ Json.writeValueAsString(middles)+"----------------|", Thread.currentThread().getName());
            Json.writeValueAsString(middles);
        } catch (JsonProcessingException e) {
            log.error("batch http request cause error,detail:{}", e);
        }

        //for ( Middle middle:middles) {
           // System.out.println(middle.toString());
       // }

    }
}

