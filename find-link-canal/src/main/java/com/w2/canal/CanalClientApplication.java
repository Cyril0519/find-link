package com.w2.canal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CanalClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(CanalClientApplication.class);
    }
}
