package com.syx.springboot.threespringboot.scheduled;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
public class TestScheduled {

    //@Scheduled(cron = "1/3 * * * * ?"))
    @Scheduled(cron = "3 * * * * ?" )
    public void testschedule(){
        System.out.println("测试定时任务");
        //
        System.out.println("测试定时任务");
        System.out.println("测试定时任务");
        System.out.println("测试定时任务");
        System.out.println("测试定时任务");
        System.out.println("测试定时任务");


        /**
         *  查看分支
         *  git branch
         *
         *  新建分支
         *  git branch syx_dev_frist
         *
         *  切换分支
         *  git checkout syx_dev_frist
         *
         *  推送分支
         *  git push origin syx_dev_frist
         *
         *
         */


    }
}