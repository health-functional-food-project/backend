package com.example.healthfunctionalfood.dto;

import com.example.healthfunctionalfood.domain.common.Image;
import lombok.*;

import java.util.List;

public class ProductRankingResponseDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @RequiredArgsConstructor
    public static class RankingHomeContainer {

        private List<RankingItem> expertRankingList;

        private RakingList ingredientsRankingList;

        private RakingList healthConcernRankingList;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    @RequiredArgsConstructor
    public static class RakingList {

        private List<String> category;

        private List<RankingItem> rankingItemList;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @RequiredArgsConstructor
    public static class RankingItem {

        private Long productId;

        private String productName;

        private String companyName;

        private Image image;

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
