package com.example.scenergynotification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ScenergyNotificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScenergyNotificationApplication.class, args);
	}

}
