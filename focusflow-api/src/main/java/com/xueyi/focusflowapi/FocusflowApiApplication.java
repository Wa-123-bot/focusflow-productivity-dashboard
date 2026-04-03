package com.xueyi.focusflowapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.xueyi")
@EnableJpaRepositories(basePackages = "com.xueyi.repository")
@EntityScan(basePackages = "com.xueyi.model")
public class FocusflowApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FocusflowApiApplication.class, args);
    }
}