package com.example.healthfunctionalfood.service;

import com.example.healthfunctionalfood.dto.ProductRankingResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRankingService {

    List<ProductRankingResponseDto.RankingItem> getExpertRanking(Pageable pageable);

    List<ProductRankingResponseDto.RankingItem> getIngredientsRanking(Pageable pageable, String ingredient);

    List<ProductRankingResponseDto.RankingItem> getHealthConcernRanking(Pageable pageable, String healthConcern);

    List<ProductRankingResponseDto.RankingItem> getRandomProductList(Pageable pageable);
}
