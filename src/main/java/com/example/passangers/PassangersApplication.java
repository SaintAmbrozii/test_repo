package com.example.passangers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class PassangersApplication {

    public static void main(String[] args) {
        SpringApplication.run(PassangersApplication.class, args);
    }

}
