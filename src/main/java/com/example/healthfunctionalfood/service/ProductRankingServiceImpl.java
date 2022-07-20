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
    public List<ProductRankingResponseDto.RankingListItem> getExpertRanking(Pageable pageable) {
        return null;
    }

    @Override
    public List<ProductRankingResponseDto.RankingListItem> getIngredientsRanking(Pageable pageable, String ingredient) {
        return null;
    }

    @Override
    public List<ProductRankingResponseDto.RankingListItem> getHealthConcernRanking(Pageable pageable, String healthConcern) {
        return null;
    }

    @Override
    public List<ProductRankingResponseDto.RankingListItem> getRandomProductList(Pageable pageable) {
        return null;
    }
}
