package br.edu.uepb.example.secondmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEurekaClient
public class professorConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(professorConsumerApplication.class, args);
	}

}