package com.example.healthfunctionalfood.repository;

import com.example.healthfunctionalfood.dto.CompareResponseDto;
import com.example.healthfunctionalfood.dto.HomeResponseDto;

import java.util.List;

public interface CustomExpertReviewRepository {

    List<String> getIngredientsForCategory();

    List<String>getHealthConcernsForCategory();

    List<HomeResponseDto.ExpertReview> getExpertReviewForMain();

    List<CompareResponseDto.prosConsTag> getAllProsConsTag(Long productId);

}
