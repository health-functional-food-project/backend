package com.example.healthfunctionalfood.repository;

import com.example.healthfunctionalfood.domain.openapi.HealthToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HealthTokenRepository  extends JpaRepository<HealthToken, Long> {
    Optional<HealthToken> findByUserId(Long id);
}
