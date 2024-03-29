package com.example.healthfunctionalfood.repository;

import com.example.healthfunctionalfood.domain.product.QProduct;
import com.example.healthfunctionalfood.domain.review.QExpertReview;
import com.example.healthfunctionalfood.dto.ProductRankingResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
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
        QProduct product = QProduct.product;
        return jpaQueryFactory.select(Projections.constructor(ProductRankingResponseDto.RankingItem.class,
                        product.id,
                        product.productName,
                        product.companyName,
                        product.image,
                        product.expertReviewAvg,
                        product.customerReviewAvg
                )).from(product)
                .join(product.expertReviewList, QExpertReview.expertReview)
                .orderBy(product.expertReviewAvg.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<ProductRankingResponseDto.RankingItem> getIngredientsRanking(String ingredient, Pageable pageable) {
        QProduct product = QProduct.product;

        return jpaQueryFactory.select(Projections.constructor(ProductRankingResponseDto.RankingItem.class,
                        product.id,
                        product.productName,
                        product.companyName,
                        product.image,
                        product.expertReviewAvg,
                        product.customerReviewAvg))
                .from(product)
                .join(product.expertReviewList, QExpertReview.expertReview)
                .where(product.primaryIngredients.contains(ingredient))
                .orderBy(product.expertReviewAvg.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<ProductRankingResponseDto.RankingItem> getIngredientsRanking(String ingredient) {
        QProduct product = QProduct.product;

        return jpaQueryFactory.select(Projections.constructor(ProductRankingResponseDto.RankingItem.class,
                        product.id,
                        product.productName,
                        product.companyName,
                        product.image,
                        product.expertReviewAvg,
                        product.customerReviewAvg))
                .from(product)
                .join(product.expertReviewList, QExpertReview.expertReview)
                .where(product.primaryIngredients.contains(ingredient))
                .orderBy(product.expertReviewAvg.desc())
                .limit(3)
                .fetch();
    }

    @Override
    public List<ProductRankingResponseDto.RankingItem> getHealthConcernRanking(String healthConcern, Pageable pageable) {
        QProduct product = QProduct.product;

        return jpaQueryFactory.select(Projections.constructor(ProductRankingResponseDto.RankingItem.class,
                        product.id,
                        product.productName,
                        product.companyName,
                        product.image,
                        product.expertReviewAvg,
                        product.customerReviewAvg))
                .from(product)
                .join(product.expertReviewList, QExpertReview.expertReview)
                .where(product.funcContent.contains(healthConcern))
                .orderBy(product.expertReviewAvg.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<ProductRankingResponseDto.RankingItem> getHealthConcernRanking(String healthConcern) {
        QProduct product = QProduct.product;

        return jpaQueryFactory.select(Projections.constructor(ProductRankingResponseDto.RankingItem.class,
                        product.id,
                        product.productName,
                        product.companyName,
                        product.image,
                        product.expertReviewAvg,
                        product.customerReviewAvg))
                .from(product)
                .join(product.expertReviewList, QExpertReview.expertReview)
                .where(product.funcContent.contains(healthConcern))
                .orderBy(product.expertReviewAvg.desc())
                .limit(3)
                .fetch();
    }


    @Override
    public List<ProductRankingResponseDto.RankingItem> getRandomProduct(Pageable pageable) {
        QProduct product = QProduct.product;

        return jpaQueryFactory.select(Projections.constructor(ProductRankingResponseDto.RankingItem.class,
                product.id,
                product.productName,
                product.companyName,
                product.image,
                product.expertReviewAvg,
                product.customerReviewAvg))
                .from(product)
                .orderBy(Expressions.numberTemplate(Double.class,"rand()").asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<Long> getIngredientRank(String ingredient) {
        QProduct product = QProduct.product;

        return jpaQueryFactory.select(product.id)
                .from(product)
                .where(product.primaryIngredients.contains(ingredient).and(product.expertReviewAvg.isNotNull()))
                .orderBy(product.expertReviewAvg.desc())
                .fetch();
    }

    @Override
    public List<Long> getHealthConcernRank(String healthConcern) {
        QProduct product = QProduct.product;

        return jpaQueryFactory.select(product.id)
                .from(product)
                .where(product.funcContent.contains(healthConcern).and(product.expertReviewAvg.isNotNull()))
                .orderBy(product.expertReviewAvg.desc())
                .fetch();
    }


}
