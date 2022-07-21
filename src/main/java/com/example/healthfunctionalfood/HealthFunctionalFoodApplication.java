package com.example.healthfunctionalfood;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.EntityManager;


@SpringBootApplication
@EnableJpaAuditing
public class HealthFunctionalFoodApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthFunctionalFoodApplication.class, args);
    }

    @Bean
    JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);    }
}
