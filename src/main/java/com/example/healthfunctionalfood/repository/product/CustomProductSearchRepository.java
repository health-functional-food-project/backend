package com.example.healthfunctionalfood.repository.product;

import com.example.healthfunctionalfood.domain.product.ProductSearch;

import java.util.List;

public interface CustomProductSearchRepository {
    List<ProductSearch> findByProductName(String search);

}
