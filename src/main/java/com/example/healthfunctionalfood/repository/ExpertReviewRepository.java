package com.example.healthfunctionalfood.repository;

import com.example.healthfunctionalfood.domain.review.ExpertReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExpertReviewRepository extends JpaRepository<ExpertReview, Long> {
    Optional<ExpertReview> findByIdAndUserId(Long expertReviewId, Long id);
}
