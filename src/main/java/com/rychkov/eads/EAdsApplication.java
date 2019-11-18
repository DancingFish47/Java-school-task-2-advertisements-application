package com.rychkov.eads;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class EAdsApplication {
    public static void main(String[] args) {
        SpringApplication.run(EAdsApplication.class, args);
    }

}
