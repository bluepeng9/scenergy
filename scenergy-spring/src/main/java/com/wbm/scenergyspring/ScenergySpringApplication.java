package com.wbm.scenergyspring;

import com.wbm.scenergyspring.global.config.PathProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@ConfigurationPropertiesScan
public class ScenergySpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScenergySpringApplication.class, args);
    }

}
