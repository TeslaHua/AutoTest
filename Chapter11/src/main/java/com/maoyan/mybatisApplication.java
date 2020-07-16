package com.maoyan;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PreDestroy;

@EnableScheduling
@SpringBootApplication
public class mybatisApplication {

    private static ConfigurableApplicationContext context;

    public static void main(String[] args){
        mybatisApplication.context = SpringApplication.run(mybatisApplication.class,args);
    }

    @PreDestroy
    public void close(){
        mybatisApplication.context.close();
    }
}
