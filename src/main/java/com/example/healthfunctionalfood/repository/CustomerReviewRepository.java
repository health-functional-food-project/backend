package com.example.healthfunctionalfood.repository;

import com.example.healthfunctionalfood.domain.review.CustomerReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerReviewRepository extends JpaRepository<CustomerReview, Long> {
}
