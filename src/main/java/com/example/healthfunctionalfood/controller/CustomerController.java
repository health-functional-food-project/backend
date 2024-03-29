package com.example.healthfunctionalfood.controller;

import com.example.healthfunctionalfood.advice.Success;
import com.example.healthfunctionalfood.dto.request.CustomerReviewRequestDto;
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
                                                     @RequestBody CustomerReviewRequestDto.CreateReview createReview){
        Long customerReviewId = customerService.addCustomerReview(productId, createReview);
        return new ResponseEntity<>(new Success("소비자 리뷰 작성 완료!",customerReviewId), HttpStatus.OK);
    }

    @PutMapping("{productId}/customer-review/{customerReviewId}")
    public ResponseEntity<Success> customerReviewModify(@PathVariable Long productId,
                                                        @RequestBody CustomerReviewRequestDto.CreateReview updateReview,
                                                        @PathVariable Long customerReviewId){
        customerService.modifyCustomerReview(productId, updateReview, customerReviewId);
        return new ResponseEntity<>(new Success("소비자 리뷰 수정 완료!",""), HttpStatus.OK);
    }

    @DeleteMapping("{productId}/customer-review/{customerReviewId}")
    public ResponseEntity<Success> customerReviewRemove(@PathVariable Long productId,
                                                        @PathVariable Long customerReviewId){
        customerService.removeCustomerReview(productId, customerReviewId);
        return new ResponseEntity<>(new Success("소비자 리뷰 삭제 완료!",""), HttpStatus.OK);
    }

    @GetMapping("{productId}/customer-review")
    public ResponseEntity<Success> customerReviewList(@PathVariable Long productId){
        return new ResponseEntity<>(new Success("내가 작성한 리뷰 조회 완료", customerService.findOneCustomerReview(productId)), HttpStatus.OK);
    }

    @PostMapping("{productId}/customer-review/{customerReviewId}/like")
    public ResponseEntity<Success> customerReviewLikeAdd(@PathVariable Long productId,
                                                         @PathVariable Long customerReviewId){
        customerService.addCustomerReviewLike(productId, customerReviewId);
        return new ResponseEntity<>(new Success("소비자 리뷰 좋아요 완료!",""), HttpStatus.OK);
    }

    @DeleteMapping("{productId}/customer-review/{customerReviewId}/like")
    public ResponseEntity<Success> customerReviewLikeRemove(@PathVariable Long productId,
                                                            @PathVariable Long customerReviewId){
        customerService.removeCustomerReviewLike(productId, customerReviewId);
        return new ResponseEntity<>(new Success("소비자 리뷰 좋아요 취소 완료!",""),HttpStatus.OK);
    }
}
