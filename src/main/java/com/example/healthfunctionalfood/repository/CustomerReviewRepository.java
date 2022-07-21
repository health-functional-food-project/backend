package com.example.healthfunctionalfood.repository;

import com.example.healthfunctionalfood.domain.review.CustomerReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerReviewRepository extends JpaRepository<CustomerReview, Long> {
    List<CustomerReview> findByUserId(Long id);
}
