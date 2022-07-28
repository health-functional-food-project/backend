package com.example.healthfunctionalfood.repository;

import com.example.healthfunctionalfood.domain.openapi.HealthHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HealthHistoryRepository extends JpaRepository<HealthHistory, Long> {
    List<HealthHistory> findByUserId(Long id);
}
