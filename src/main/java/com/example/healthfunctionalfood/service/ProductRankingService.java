package com.example.healthfunctionalfood.service;

import com.example.healthfunctionalfood.dto.ProductRankingResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRankingService {

    ProductRankingResponseDto.RankingHomeContainer getRankingHome(Pageable pageable);

    List<ProductRankingResponseDto.RankingItem> getExpertRanking(Pageable pageable);

    List<ProductRankingResponseDto.RankingItem> getIngredientsRanking(String ingredient, Pageable pageable);

    List<ProductRankingResponseDto.RankingItem> getHealthConcernRanking(String healthConcern, Pageable pageable);

    List<ProductRankingResponseDto.RankingItem> getRandomProductList(Pageable pageable);

    List<String> getIngredientsCategory();

//    List<String> getIngredientsCategory2();

    List<String> getHealthConcernCategory();
}
