package com.zoouniak.yoursell;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class YoursellApplication {

	public static void main(String[] args) {
		SpringApplication.run(YoursellApplication.class, args);
	}

}
