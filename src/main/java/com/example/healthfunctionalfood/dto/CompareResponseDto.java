package com.example.healthfunctionalfood.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.TreeMap;

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

        private CompareProduct product;

        private CompareExpertReview expertReview;

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

        private TreeMap<String, Long> prosTagList;

        private TreeMap<String, Long> consTagList;

    }


}
