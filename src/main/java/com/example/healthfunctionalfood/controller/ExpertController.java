package com.example.healthfunctionalfood.controller;

import com.example.healthfunctionalfood.advice.Success;
import com.example.healthfunctionalfood.dto.request.ExpertReviewRequestDto;
import com.example.healthfunctionalfood.service.review.expert.ExpertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ExpertController {

    private final ExpertService expertService;

    @PostMapping("{productId}/expert-review")
    public ResponseEntity<Success> ExpertReviewAdd(@PathVariable Long productId,
                                                   @RequestBody ExpertReviewRequestDto.CreateReview createReview){
        expertService.addExpertReview(productId, createReview);
        return new ResponseEntity<>(new Success("전문가 리뷰 등록 성공!",""), HttpStatus.OK);
    }
}
