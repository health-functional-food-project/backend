package com.example.healthfunctionalfood.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class CustomerReviewDto {

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
    }
}
