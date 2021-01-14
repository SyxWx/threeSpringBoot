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
        System.out.println("测试git-merge定时任务");
        System.out.println("测试git-merge定时任务");
        System.out.println("测试git-merge定时任务");
        System.out.println("测试git-merge定时任务");
        System.out.println("测试定时任务");
        System.out.println("1测试git-merge定时任务");
        System.out.println("2测试git-merge定时任务");
        System.out.println("3测试git-merge定时任务");

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
         *  推送分支到仓库(推送后，在远程仓库中查看到分支)
         *  git push origin syx_dev_frist
         *
         *  推送代码到仓库分支(推送后，在远程仓库所属的分支中查看代码)
         *  git push origin syx_dev_frist
         *
         *
         *  推送代码到远程仓库  从syx_dev_frist分支 推送到 syx_dev分支
         *  git push origin syx_dev_frist:syx_dev
         *
         *  获取远程仓库中的代码到本地仓库中。
         *  git pull
         *
         *  ------合并syx_dev分支的数据到master分支中
         *  在syx_dev分支中提交代码，然后push到所属分支上
         *  git push origin syx_dev
         *  切换到master分支
         *  git checkout master
         *  pull最新的代码
         *  git pull
         *  合并操作 merge
         *  git merge dev_syx
         *  推送在远程master分支
         *  git push origin master
         */


    }
}
