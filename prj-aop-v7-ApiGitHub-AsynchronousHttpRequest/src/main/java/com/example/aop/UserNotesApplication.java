package com.example.aop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class UserNotesApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserNotesApplication.class, args);
	}
}
