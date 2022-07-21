package com.example.healthfunctionalfood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HealthFunctionalFoodApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthFunctionalFoodApplication.class, args);
    }

}
