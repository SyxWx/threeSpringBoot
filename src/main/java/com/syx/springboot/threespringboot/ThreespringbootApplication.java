package com.syx.springboot.threespringboot;

import com.syx.springboot.threespringboot.auditor.Auditor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ThreespringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThreespringbootApplication.class, args);

        System.out.print("This is Three Spring boot");

       //Auditor.fire();


    }

    /**
     *  查看分支
     *  git branch
     *
     *  新建分支
     *  git branch syx_dev
     *
     *  切换分支
     *  git checkout syx_dev
     *
     *  推送分支到仓库(推送后，在远程仓库中查看到分支)
     *  git push origin syx_dev
     *
     *  推送代码到仓库分支(推送后，在远程仓库所属的分支中查看代码)
     *  git push origin syx_dev
     *
     *
     *  推送代码到远程仓库  从syx_dev分支 推送到 master 分支
     *  git push origin syx_dev:master
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
     *
     *
     *
     *   ------合并master分支的数据到syx_dev分支中
     *  切换到master分支  git pull
     *  切换到syx_dev分支
     *  git merge  master
     *
     *
     */
}
