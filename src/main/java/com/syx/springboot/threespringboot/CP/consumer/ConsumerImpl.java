package com.syx.springboot.threespringboot.CP.consumer;

import com.syx.springboot.threespringboot.middle.Middle;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
@Slf4j
public class ConsumerImpl implements Consumer {

    /**
     * 消息队列
     */
    private final BlockingQueue<Middle> queue;

    /**
     * 消费者线程池
     */
    private final ThreadPoolExecutor threadPool;

    /**
     * 开启消费线程数
     */
    private final int threadCount;

    private final CountDownLatch countDownLatch;

    public ConsumerImpl(BlockingQueue<Middle> queue) {
        this.queue = queue;

        this.threadCount =2;

        //初始化线程池,线程池大小固定
        final int[] i = new int[1];
        LinkedBlockingQueue<Runnable> threadQueue = new LinkedBlockingQueue<>(threadCount);
        this.threadPool = new ThreadPoolExecutor(threadCount, threadCount, 60, TimeUnit.SECONDS, threadQueue,
                runnable -> new Thread(runnable, "batch-put-thread-" + ++i[0]));
        this.countDownLatch = new CountDownLatch(threadCount);

        log.debug("the consumer has started");
    }

    @Override
    public void start() {
        //启动消费线程
        for (int i = 0; i < threadCount; i++) {
            threadPool.execute(new ConsumerRunnable(queue, countDownLatch));
        }
    }

    @Override
    public void gracefulStop() {
        this.stop(false);
    }

    @Override
    public void forceStop() {
        this.stop(true);
    }

    /***
     * 关闭线程池
     * @param force 是否强制关闭
     */
    private void stop(boolean force) {
        if (threadPool != null) {
            if (force) {
                // 强制退出不等待，截断消费者线程。
                threadPool.shutdownNow();
            } else {
                // 截断消费者线程
                while (!threadPool.isShutdown() || !threadPool.isTerminated()) {
                    threadPool.shutdownNow();
                }

                // 等待所有消费者线程结束
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    log.error("An error occurred waiting for the consumer thread to close", e);
                }
            }
        }
    }
}
