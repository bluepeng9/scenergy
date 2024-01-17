package com.wbm.scenergyspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ScenergySpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScenergySpringApplication.class, args);
    }

}
