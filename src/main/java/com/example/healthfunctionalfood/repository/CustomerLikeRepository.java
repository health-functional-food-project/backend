package com.example.healthfunctionalfood.repository;

import com.example.healthfunctionalfood.domain.review.CustomerLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerLikeRepository extends JpaRepository<CustomerLike, Long> {
    Optional<CustomerLike> CustomerReviewIdAndUserId(Long customerReviewId, Long id);

    void deleteByCustomerReviewIdAndUserId(Long customerReviewId, Long id);
}
