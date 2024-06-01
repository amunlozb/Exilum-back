package com.exilum.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

@SpringBootApplication
public class ExilumApplication {

	private static final Logger logger = LoggerFactory.getLogger(ExilumApplication.class);

	public static void main(final String[] args) throws Exception {
		String port = System.getenv("PORT");
		if (port == null) {
			port = "8080";
			logger.warn("$PORT environment variable not set, defaulting to 8080");
		}
		SpringApplication app = new SpringApplication(ExilumApplication.class);
		app.setDefaultProperties(Collections.singletonMap("server.port", port));

		app.run(args);
        logger.info("Container started succesfully! Listening to HTTP requests on {}", port);
	}
}
