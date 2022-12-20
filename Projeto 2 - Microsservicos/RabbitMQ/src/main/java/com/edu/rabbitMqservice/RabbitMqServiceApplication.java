package com.edu.rabbitMqservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class RabbitMqServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitMqServiceApplication.class, args);
	}

}
