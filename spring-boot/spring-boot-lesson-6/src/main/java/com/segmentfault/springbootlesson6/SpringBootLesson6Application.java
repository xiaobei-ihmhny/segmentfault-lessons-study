package com.segmentfault.springbootlesson6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.segmentfault.controller")
@ComponentScan("com.segmentfault.service")
public class SpringBootLesson6Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootLesson6Application.class, args);
    }

}
