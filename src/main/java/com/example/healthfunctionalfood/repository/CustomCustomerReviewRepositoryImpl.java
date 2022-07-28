package com.example.healthfunctionalfood.repository;

import com.example.healthfunctionalfood.domain.review.QCustomerReview;
import com.example.healthfunctionalfood.dto.HomeResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomCustomerReviewRepositoryImpl implements CustomCustomerReviewRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<HomeResponseDto.CustomerReview> getCustomerReview () {
        QCustomerReview customerReview = QCustomerReview.customerReview;

        return jpaQueryFactory.select(Projections.constructor(HomeResponseDto.CustomerReview.class,
                customerReview.product.id,
                customerReview.product.productName,
                customerReview.product.customerReviewAvg,
                customerReview.pros.as("comment"),
                customerReview.product.image))
                .from(customerReview)
                .orderBy(Expressions.numberTemplate(Double.class,"rand()").asc())
                .limit(12)
                .fetch();
    }

}
