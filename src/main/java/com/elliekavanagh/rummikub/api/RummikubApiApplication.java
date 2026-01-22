package com.elliekavanagh.rummikub.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.elliekavanagh.rummikub")
public class RummikubApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(RummikubApiApplication.class, args);
    }
}
