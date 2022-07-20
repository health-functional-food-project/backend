package com.example.healthfunctionalfood.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

public class ProductRankingResponseDto {

    @Getter
    @RequiredArgsConstructor
    public static class RankingMainContainer {

        private RakingList expertRankingList;

        private RakingList ingredientsRankingList;

        private RakingList healthConcernRankingList;

        private RakingList ganderAndAgeRankingList;

    }

    @Getter
    @RequiredArgsConstructor
    public static class RakingList {

        private List<String> category;

        private List<RankingItem> rankingListItemList;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @RequiredArgsConstructor
    public static class RankingItem {

        private Long productId;

        private String productName;

        private String companyName;

        private Double expertStarRatingAve;

        private Double customerStarRatingAve;
    }

    @Getter
    @AllArgsConstructor
    public static class CategoryReviewCount {

        private String primaryIngredients;

        private Long reviewCount;
    }

}
