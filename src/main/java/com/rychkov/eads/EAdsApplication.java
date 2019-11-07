package com.rychkov.eads;

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
