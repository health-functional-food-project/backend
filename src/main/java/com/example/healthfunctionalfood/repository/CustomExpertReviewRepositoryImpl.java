package com.example.healthfunctionalfood.repository;

import com.example.healthfunctionalfood.domain.product.QProduct;
import com.example.healthfunctionalfood.domain.review.QExpertReview;
import com.example.healthfunctionalfood.dto.CompareResponseDto;
import com.example.healthfunctionalfood.dto.HomeResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomExpertReviewRepositoryImpl implements CustomExpertReviewRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<String> getIngredientsForCategory() {
        QExpertReview expertReview = QExpertReview.expertReview;

        return jpaQueryFactory.select(expertReview.product.primaryIngredients)
                .from(expertReview)
                .fetch();
    }

    @Override
    public List<String>getHealthConcernsForCategory() {
        QExpertReview expertReview = QExpertReview.expertReview;

        return jpaQueryFactory.select(expertReview.product.funcContent)
                .from(expertReview)
                .fetch();
    }

    @Override
    public List<HomeResponseDto.ExpertReview> getExpertReviewForMain() {
        QExpertReview expertReview = QExpertReview.expertReview;

        return jpaQueryFactory.select(Projections.constructor(HomeResponseDto.ExpertReview.class,
                        expertReview.product.id.as("productId"),
                        expertReview.product.productName.as("productName"),
                        expertReview.product.expertReviewAvg.as("starRating"),
                        expertReview.comment.as("comment"),
                        expertReview.product.image.as("productImage")))
                .from(expertReview)
//                .join(expertReview, QProduct.product.expertReviewList)
                .orderBy(Expressions.numberTemplate(Double.class,"rand()").asc())
                .limit(12)
                .fetch();
    }

    @Override
    public List<CompareResponseDto.prosConsTag> getAllProsConsTag(Long productId) {
        QExpertReview expertReview = QExpertReview.expertReview;

        return jpaQueryFactory.select(Projections.constructor(CompareResponseDto.prosConsTag.class,
                expertReview.pros, expertReview.cons))
                .from(expertReview)
                .join(expertReview.product, QProduct.product)
                .where(expertReview.product.id.eq(productId))
                .fetch();
    }


}
