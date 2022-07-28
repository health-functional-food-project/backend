package com.example.healthfunctionalfood.repository;

import com.example.healthfunctionalfood.domain.openapi.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {
    List<Diagnosis> findByUserId(Long id);
}
