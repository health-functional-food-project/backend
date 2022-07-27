package com.example.healthfunctionalfood.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

public class CompareResponseDto {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class MainContainer {

        private ProductContainer firstProductForCompare;

        private ProductContainer secondProductForCompare;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ProductContainer {

        private CompareProduct productDetail;

        private CompareExpertReview expertReviewSummary;

        private Double customerStarRatingAvg;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class CompareProduct {

        private Long productId;

        private String companyName;

        private String productName;

        private String primaryIngredient;

        private List<Ranking> ingredientRankingList;

        private List<Ranking> healthConcernRankingList;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Ranking {

        private String content;

        private Long award;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class CompareExpertReview {

        private Double starRatingAvg;

        private Map<String, Long> prosTagMap;

        private Map<String, Long> consTagMap;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class prosConsTag {
        private String pros;
        private String cons;
    }


}
