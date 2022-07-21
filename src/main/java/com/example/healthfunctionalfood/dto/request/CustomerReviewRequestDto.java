package com.example.healthfunctionalfood.dto.request;

import com.example.healthfunctionalfood.domain.product.Product;
import com.example.healthfunctionalfood.domain.review.CustomerReview;
import com.example.healthfunctionalfood.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CustomerReviewRequestDto {

    @NoArgsConstructor
    @Getter
    public static class CreateReview {

        private Integer starRating;

        private Boolean takingCheck;

        private Boolean familyTakingCheck;

        private String pros;

        private String cons;

        private Boolean exposureStatus;

        private String keyword;

        @Builder
        public CreateReview(Integer starRating, Boolean takingCheck, Boolean familyTakingCheck, String pros, String cons, Boolean exposureStatus, String keyword) {
            this.starRating = starRating;
            this.takingCheck = takingCheck;
            this.familyTakingCheck = familyTakingCheck;
            this.pros = pros;
            this.cons = cons;
            this.exposureStatus = exposureStatus;
            this.keyword = keyword;
        }

        public CustomerReview toEntity(Product product, User user){
            return CustomerReview.builder()
                    .takingCheck(takingCheck)
                    .cons(cons)
                    .product(product)
                    .exposureStatus(exposureStatus)
                    .familyTakingCheck(familyTakingCheck)
                    .pros(pros)
                    .starRating(starRating)
                    .user(user)
                    .cons(cons)
                    .takingCheck(takingCheck)
                    .keyword(keyword)
                    .build();
        }
    }
}
