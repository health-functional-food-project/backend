package com.example.healthfunctionalfood.service.product;

import com.example.healthfunctionalfood.advice.exception.ApiRequestException;
import com.example.healthfunctionalfood.domain.product.Product;
import com.example.healthfunctionalfood.domain.product.ProductWishList;
import com.example.healthfunctionalfood.domain.user.User;
import com.example.healthfunctionalfood.dto.response.ProductResponseDto;
import com.example.healthfunctionalfood.repository.ProductRepository;
import com.example.healthfunctionalfood.repository.ProductWishListRepository;
import com.example.healthfunctionalfood.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final ProductWishListRepository productWishListRepository;

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

    @Override
    @Transactional
    public void addProductWishList(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new ApiRequestException("해당 상품이 존재하지 않습니다."));

        //로그인 미완성으로 임시 하드코딩
        User user = userRepository.findById(1L).get();

        Optional<ProductWishList> productWishListOptional = productWishListRepository.findByProductIdAndUserId(productId, user.getId());

        if (productWishListOptional.isEmpty()) {
            ProductWishList productWishList = ProductWishList.builder()
                    .user(user)
                    .product(product)
                    .build();
            productWishListRepository.save(productWishList);
        } else {
            throw new ApiRequestException("이미 찜한 상품입니다.");
        }
    }

    @Override
    @Transactional
    public void removeProductWishList(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new ApiRequestException("해당 상품이 존재하지 않습니다."));

        //로그인 미완성으로 임시 하드코딩
        User user = userRepository.findById(1L).get();

        Optional<ProductWishList> productWishListOptional = productWishListRepository.findByProductIdAndUserId(productId, user.getId());
        if(productWishListOptional.isPresent()){
            productWishListRepository.deleteByProductIdAndUserId(productId, user.getId());
        }else {
            throw new ApiRequestException("찜한 상품이 아닙니다.");
        }
    }
}
