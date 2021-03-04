package com.syx.springboot.threespringboot.auditor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 调度器
 * @author yutyi
 */
public class Auditor {

    protected static Logger logger = LoggerFactory.getLogger(Auditor.class);



    private static int minPeriod = 30;
    public static List<TaskRunnable> taskRunnableList = new ArrayList<>();

    public static void fire() {

            TaskRunnable taskRunner = new TaskRunnable();
            taskRunner.setPeriod(minPeriod);
            new Thread(taskRunner, "default" + taskRunner.getPeriod()).start();
            taskRunnableList.add(taskRunner);
            logger.info("==========>start default thread: {}", taskRunner.getPeriod());

    }
}

