package com.ccs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
@RefreshScope
public class Service_A1Application {

    public static void main(String[] args) {
        SpringApplication.run(Service_A1Application.class, args);
    }

}