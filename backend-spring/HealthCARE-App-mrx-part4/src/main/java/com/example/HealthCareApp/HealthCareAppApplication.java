package com.example.HealthCareApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@EnableCaching
@SpringBootApplication
public class HealthCareAppApplication {

    public static void main(String[] args) {

        SpringApplication.run(HealthCareAppApplication.class, args);
    }

}


