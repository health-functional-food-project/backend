package com.example.healthfunctionalfood.repository;

import com.example.healthfunctionalfood.domain.product.QProduct;
import com.example.healthfunctionalfood.dto.ProductRankingResponseDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomProductRepositoryImpl implements CustomProductRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ProductRankingResponseDto.RankingItem> getExpertStarRatingAvgRanking(Pageable pageable) {
        QProduct qProduct = QProduct.product;
        return null;
    }

    @Override
    public List<ProductRankingResponseDto.RankingItem> getIngredientsRanking(String ingredient, Pageable pageable) {
        return null;
    }

    @Override
    public List<ProductRankingResponseDto.RankingItem> getHealthConcern(String healthConcern, Pageable pageable) {
        return null;
    }
}
