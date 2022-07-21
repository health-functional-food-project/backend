package com.example.healthfunctionalfood.service.product;

import com.example.healthfunctionalfood.dto.response.ProductResponseDto;


public interface ProductService {
    ProductResponseDto.SearchAndCount findAllProductSearchList(String search);

    void addProductWishList(Long productId);

    void removeProductWishList(Long productId);
}
