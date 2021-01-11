package com.syx.springboot.threespringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ThreespringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThreespringbootApplication.class, args);

        System.out.print("This is Three Spring boot");


    }

}
