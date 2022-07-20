package com.example.healthfunctionalfood.service.customer;

import com.example.healthfunctionalfood.dto.request.CustomerReviewRequestDto;
import com.example.healthfunctionalfood.dto.response.CustomerReviewResponseDto;

import java.util.List;

public interface CustomerService {
    Long addCustomerReview(Long productId, CustomerReviewRequestDto.CreateReview createReview);

    void modifyCustomerReview(Long productId, CustomerReviewRequestDto.CreateReview updateReview, Long customerReviewId );

    void removeCustomerReview(Long productId, Long customerReviewId);

    List<CustomerReviewResponseDto.MyReview> findOneCustomerReview(Long productId);
}
