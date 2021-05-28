package com.example.blogwebsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BlogwebsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogwebsiteApplication.class, args);
    }

}
