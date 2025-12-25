package com.austain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling  // 启用定时任务
public class English_back_system_Application {

    public static void main(String[] args) {
        SpringApplication.run(English_back_system_Application.class, args);
    }

}
