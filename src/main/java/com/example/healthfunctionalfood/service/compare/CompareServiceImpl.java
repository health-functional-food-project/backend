package com.example.healthfunctionalfood.service.compare;

import com.example.healthfunctionalfood.dto.CompareResponseDto;
import com.example.healthfunctionalfood.repository.CustomProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompareServiceImpl implements CompareService{

    private final CustomProductRepository customProductRepository;

    public CompareResponseDto.MainContainer getCompareProduct(Long firstProductId, Long secondProductId) {

        return null;

    }

    public CompareResponseDto.ProductContainer getProductInfo(Long productId) {

    return null;

    }
}
