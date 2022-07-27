package com.example.healthfunctionalfood.repository;

import com.example.healthfunctionalfood.dto.HomeResponseDto;

import java.util.List;

public interface CustomCustomerReviewRepository {
    List<HomeResponseDto.CustomerReview> getCustomerReview ();
}
