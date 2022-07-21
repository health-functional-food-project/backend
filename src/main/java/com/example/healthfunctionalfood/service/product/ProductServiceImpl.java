package com.example.healthfunctionalfood.service.product;

import com.example.healthfunctionalfood.domain.product.Product;
import com.example.healthfunctionalfood.dto.response.ProductResponseDto;
import com.example.healthfunctionalfood.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    @Override
    public ProductResponseDto.SearchAndCount findAllProductSearchList(String search) {

        Integer ProductCount = productRepository.findByProductCount(search);

        List<ProductResponseDto.Search> productSearchList = productRepository.findByProductName(search).stream()
                .map(ProductResponseDto.Search::new)
                .collect(Collectors.toList());

        return ProductResponseDto.SearchAndCount.builder()
                .productCount(ProductCount)
                .searchList(productSearchList)
                .build();
    }
}
