package com.example.healthfunctionalfood.dto;

import com.example.healthfunctionalfood.domain.common.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class HomeResponseDto {


    @Getter
    @Builder
    @AllArgsConstructor
    public static class MainContainer {

        private List<ExpertRecommend> expertRecommendList;

        private List<ExpertReview> expertReviewList;

        private PopularProductRanking popularProductRanking;

        private IngredientTrend ingredientTrend;

        private List<CustomerReview> customerReviewList;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ExpertRecommend {

        private Long productId;

        private String productName;

        private String companyName;

        private Image image;

        private Double starRating;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ExpertReview {

        private Long productId;

        private String productName;

        private Double starRating;

        private String comment;

        private Image productImage;

//        private Long expertCount;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class PopularProductRanking {

        private List<String> categoryList;

        private List<ProductRankingResponseDto.RankingItem> productList;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class RankingProduct {

        private Long productId;

        private String productName;

        private String companyName;

        private Image image;

        private Double expertStarRatingAvg;

        private Double customerStarRatingAvg;

        private Long customerReviewCount;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class IngredientTrend {

        private LocalDate asOfDate;

        private List<String> ingredientList;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class CustomerReview {

        private Long productId;

        private String productName;

        private Double customerReviewAvg;

        private String comment;

        private Image image;
    }




}
