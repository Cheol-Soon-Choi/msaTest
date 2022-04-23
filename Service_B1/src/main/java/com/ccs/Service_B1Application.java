package com.ccs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableHystrix
public class Service_B1Application {
    public static void main(String[] args) {
        SpringApplication.run(Service_B1Application.class, args);
    }
}
