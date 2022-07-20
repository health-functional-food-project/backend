package com.example.healthfunctionalfood.service;

import com.example.healthfunctionalfood.dto.ProductRankingResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRankingService {

    List<ProductRankingResponseDto.RankingListItem> getExpertRanking(Pageable pageable);

    List<ProductRankingResponseDto.RankingListItem> getIngredientsRanking(Pageable pageable, String ingredient);

    List<ProductRankingResponseDto.RankingListItem> getHealthConcernRanking(Pageable pageable, String healthConcern);

    List<ProductRankingResponseDto.RankingListItem> getRandomProductList(Pageable pageable);
}
