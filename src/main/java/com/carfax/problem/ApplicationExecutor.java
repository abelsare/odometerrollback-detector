package com.carfax.problem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

/**
 * Executor to spin up Spring Boot application for the API endpoint
 *
 */
@Slf4j
@Configuration
@SpringBootApplication
public class ApplicationExecutor {
	
	public static void main(String[] args) {
		SpringApplication.run(ApplicationExecutor.class, args);
		log.info("Odometer Rollback Detector started .....");
	}

}
