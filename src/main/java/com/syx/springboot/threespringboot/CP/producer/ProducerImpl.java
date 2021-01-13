package com.syx.springboot.threespringboot.CP.producer;

import com.syx.springboot.threespringboot.middle.Middle;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class ProducerImpl implements Producer {


    /**
     * 消息队列
     */
    private final BlockingQueue<Middle> queue;

    public ProducerImpl(BlockingQueue<Middle> queue) {
        this.queue = queue;
        log.debug("the producer has started!");
    }

    /**
     * 是否禁止写入队列
     */
    private final AtomicBoolean forbiddenWrite = new AtomicBoolean(false);


    @Override
    public void send(Middle middle) {
        try {
            queue.put(middle);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void forbiddenSend() {

    }
}
