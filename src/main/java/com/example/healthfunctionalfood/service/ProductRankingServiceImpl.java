package com.example.healthfunctionalfood.service;

import com.example.healthfunctionalfood.dto.ProductRankingResponseDto;
import com.example.healthfunctionalfood.repository.CustomCustomerReviewRepository;
import com.example.healthfunctionalfood.repository.CustomExpertReviewRepository;
import com.example.healthfunctionalfood.repository.CustomProductRepository;
import com.example.healthfunctionalfood.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductRankingServiceImpl implements ProductRankingService{

    private final UserRepository userRepository;
    private final CustomExpertReviewRepository customExpertReviewRepository;
    private final CustomCustomerReviewRepository customCustomerReviewRepository;
    private final CustomProductRepository customProductRepository;


    @Override
    public List<ProductRankingResponseDto.RankingItem> getExpertRanking(Pageable pageable) {
        List<ProductRankingResponseDto.RankingItem> expertStarRatingAvgRanking = customProductRepository.getExpertStarRatingAvgRanking(pageable);

        for (ProductRankingResponseDto.RankingItem rankingItem : expertStarRatingAvgRanking) {
            log.info("getProductName = {}", rankingItem.getProductName());
        }

        return expertStarRatingAvgRanking;
    }

    @Override
    public List<ProductRankingResponseDto.RankingItem> getIngredientsRanking(String ingredient, Pageable pageable) {
        List<ProductRankingResponseDto.RankingItem> ingredientsRanking = customProductRepository.getIngredientsRanking(ingredient, pageable);

        for (ProductRankingResponseDto.RankingItem rankingItem : ingredientsRanking) {
            log.info("getProductName = {}", rankingItem.getProductName());
        }

        return ingredientsRanking;
    }

    @Override
    public List<ProductRankingResponseDto.RankingItem> getHealthConcernRanking(String healthConcern, Pageable pageable) {
        List<ProductRankingResponseDto.RankingItem> healthConcernRanking = customProductRepository.getHealthConcernRanking(healthConcern, pageable);

        for (ProductRankingResponseDto.RankingItem rankingItem : healthConcernRanking) {
            log.info("getProductName = {}", rankingItem.getProductName());
        }

        return healthConcernRanking;
    }

    @Override
    public List<ProductRankingResponseDto.RankingItem> getRandomProductList(Pageable pageable) {
        return null;
    }
}
