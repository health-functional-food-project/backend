package com.example.healthfunctionalfood.service.customer;

import com.example.healthfunctionalfood.dto.CustomerReviewDto;

public interface CustomerService {
    Long addCustomerReview(Long productId, CustomerReviewDto.CreateReview createReview);
}
