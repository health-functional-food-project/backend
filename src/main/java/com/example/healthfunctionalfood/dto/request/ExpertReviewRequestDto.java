package com.example.healthfunctionalfood.dto.request;

import com.example.healthfunctionalfood.domain.product.Product;
import com.example.healthfunctionalfood.domain.review.ExpertReview;
import com.example.healthfunctionalfood.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ExpertReviewRequestDto {

    @NoArgsConstructor
    @Getter
    public static class CreateReview{

        private String drugStore;

        private double starRating;

        private String comment;

        private String cons;

        private String pros;

        private String precautions;

        public ExpertReview toEntity(Product product, User user){
            return ExpertReview.builder()
                    .starRating(starRating)
                    .product(product)
                    .comment(comment)
                    .user(user)
                    .cons(cons)
                    .precautions(precautions)
                    .pros(pros)
                    .drugStore(drugStore)
                    .build();
        }
    }
}
