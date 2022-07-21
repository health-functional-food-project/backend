package com.example.healthfunctionalfood.repository;

import com.example.healthfunctionalfood.domain.product.Product;
import com.example.healthfunctionalfood.domain.review.ExpertKeyword;

import java.util.List;

public interface CustomExpertKeywordRepository {

    List<ExpertKeyword> getExpertKeyword(Product product);
}
