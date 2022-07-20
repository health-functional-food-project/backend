package com.example.healthfunctionalfood.controller;

import com.example.healthfunctionalfood.advice.Success;
import com.example.healthfunctionalfood.dto.CustomerReviewDto;
import com.example.healthfunctionalfood.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("{productId}/customer-review")
    public ResponseEntity<Success> customerReviewAdd(@PathVariable Long productId,
                                                     @RequestBody CustomerReviewDto.CreateReview createReview){
        Long customerReviewId = customerService.addCustomerReview(productId, createReview);
        return new ResponseEntity<>(new Success("소비자 리뷰 작성 완료!",customerReviewId), HttpStatus.OK);
    }

    @PutMapping("{productId}/customer-review/{customerReviewId}")
    public ResponseEntity<Success> customerReviewModify(@PathVariable Long productId,
                                                        @RequestBody CustomerReviewDto.CreateReview updateReview,
                                                        @PathVariable Long customerReviewId){
        customerService.modifyCustomerReview(productId, updateReview, customerReviewId);
        return new ResponseEntity<>(new Success("소비자 리뷰 수정 완료!",""), HttpStatus.OK);
    }
}
