package com.bosch.transformer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendDemoApplication.class, args);
		System.out.println("App is live!");
	}

}
