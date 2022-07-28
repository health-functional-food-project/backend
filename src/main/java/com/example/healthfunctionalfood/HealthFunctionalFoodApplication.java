package com.example.healthfunctionalfood;

import com.example.healthfunctionalfood.repository.product.ProductSearchRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories(excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = ProductSearchRepository.class))
public class HealthFunctionalFoodApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthFunctionalFoodApplication.class, args);
    }

}
