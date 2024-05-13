package com.exilum.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//TODO: remove exlusion for db connection
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ExilumApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExilumApplication.class, args);
	}
}
