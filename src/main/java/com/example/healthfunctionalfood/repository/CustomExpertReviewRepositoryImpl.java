package com.example.healthfunctionalfood.repository;

import com.example.healthfunctionalfood.domain.review.QExpertReview;
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
}
