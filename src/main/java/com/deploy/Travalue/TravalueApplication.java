package com.deploy.Travalue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TravalueApplication {
	public static void main(String[] args) {
		SpringApplication.run(TravalueApplication.class, args);
	}
}
