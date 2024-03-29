package com.example.healthfunctionalfood.service.review.expert;

import com.example.healthfunctionalfood.dto.request.ExpertReviewRequestDto;

public interface ExpertService {
    void addExpertReview(Long productId, ExpertReviewRequestDto.CreateReview createReview);

    void modifyExpertReview(Long productId, Long expertReviewId, ExpertReviewRequestDto.CreateReview createReview);

    void removeExpertReview(Long productId, Long expertReviewId);
}
