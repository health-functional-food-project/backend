package com.example.healthfunctionalfood.repository;

import com.example.healthfunctionalfood.dto.ProductRankingResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomProductRepository {

    List<ProductRankingResponseDto.RankingItem> getExpertStarRatingAvgRanking(Pageable pageable);

    List<ProductRankingResponseDto.RankingItem> getIngredientsRanking(String ingredient, Pageable pageable);
    List<ProductRankingResponseDto.RankingItem> getIngredientsRanking(String ingredient);

    List<ProductRankingResponseDto.RankingItem> getHealthConcernRanking(String healthConcern, Pageable pageable);
    List<ProductRankingResponseDto.RankingItem> getHealthConcernRanking(String healthConcern);

    List<ProductRankingResponseDto.RankingItem> getRandomProduct(Pageable pageable);


}
