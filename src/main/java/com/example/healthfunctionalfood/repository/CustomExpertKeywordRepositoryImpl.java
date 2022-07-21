package com.example.healthfunctionalfood.repository;

import com.example.healthfunctionalfood.domain.product.Product;
import com.example.healthfunctionalfood.domain.review.ExpertKeyword;
import com.example.healthfunctionalfood.domain.review.QExpertKeyword;
import com.example.healthfunctionalfood.domain.review.QExpertReview;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomExpertKeywordRepositoryImpl implements CustomExpertKeywordRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ExpertKeyword> getExpertKeyword(Product product) {
        QExpertKeyword expertKeyword = QExpertKeyword.expertKeyword;

        return jpaQueryFactory.select(expertKeyword)
                .where(expertKeyword.expertReview.product.eq(product))
                .join(expertKeyword.expertReview, QExpertReview.expertReview)
                .fetch();
    }
}
