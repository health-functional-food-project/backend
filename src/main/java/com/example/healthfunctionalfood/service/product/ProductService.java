package com.example.healthfunctionalfood.service.product;

import com.example.healthfunctionalfood.dto.response.ProductResponseDto;
import org.springframework.data.domain.Pageable;


public interface ProductService {
    ProductResponseDto.SearchAndCount findAllProductSearchList(String search);

    void addProduct();

    ProductResponseDto.SearchAndCount findAllProductSearchListElk(String productName);
}
