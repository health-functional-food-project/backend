package com.example.healthfunctionalfood.dto.response;

import com.example.healthfunctionalfood.domain.review.CustomerReview;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class CustomerReviewResponseDto {

    @Getter
    @NoArgsConstructor
    public static class MyReview {

        private Long customerReview_id;

        private double starRating;

        private Boolean takingCheck;

        private Boolean familyTakingCheck;

        private String pros;

        private String cons;

        private Boolean exposureStatus;

        private LocalDateTime modifiedAt;

        private Long productId;

        private String productImage;

        private int likeCount;

        private String companyName;

        private String itemName;

        private String keyword;

        public MyReview(CustomerReview customerReview) {
            this.customerReview_id = customerReview.getId();
            this.starRating = customerReview.getStarRating();
            this.takingCheck = customerReview.getTakingCheck();
            this.familyTakingCheck = customerReview.getFamilyTakingCheck();
            this.pros = customerReview.getPros();
            this.cons = customerReview.getCons();
            this.exposureStatus = customerReview.getExposureStatus();
            this.keyword = customerReview.getKeyword();
            this.modifiedAt = customerReview.getModifiedAt();
            this.productId = customerReview.getProduct().getId();
            this.companyName = customerReview.getProduct().getCompanyName();
            this.itemName = customerReview.getProduct().getProductName();
            this.productImage = customerReview.getProduct().getImage().getFile_store_course();
            this.likeCount = customerReview.getCustomerLikes().size();

        }
    }
}
