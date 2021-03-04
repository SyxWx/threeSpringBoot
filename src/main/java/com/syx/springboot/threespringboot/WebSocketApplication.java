package com.syx.springboot.threespringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.apache.catalina.filters.CorsFilter;


@SpringBootApplication
public class WebSocketApplication {

    public static void main(String[] args){
        SpringApplication.run(WebSocketApplication.class,args);
    }

}
