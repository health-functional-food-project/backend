package com.example.healthfunctionalfood.service.product;

import com.example.healthfunctionalfood.advice.exception.ApiRequestException;
import com.example.healthfunctionalfood.domain.product.Product;
import com.example.healthfunctionalfood.domain.product.ProductSearch;
import com.example.healthfunctionalfood.dto.response.ProductResponseDto;
import com.example.healthfunctionalfood.repository.product.ProductRepository;

import com.example.healthfunctionalfood.repository.product.ProductSearchRepository;

import com.example.healthfunctionalfood.domain.product.ProductWishList;
import com.example.healthfunctionalfood.domain.user.User;
import com.example.healthfunctionalfood.repository.ProductWishListRepository;
import com.example.healthfunctionalfood.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductSearchRepository productSearchRepository;
    private final UserRepository userRepository;
    private final ProductWishListRepository productWishListRepository;
    @Override
    public ProductResponseDto.SearchAndCount findAllProductSearchList(String search) {

        List<ProductResponseDto.Search> productSearchList = productRepository.findByProductNames(search).stream()
                .map(ProductResponseDto.Search::new)
                .collect(Collectors.toList());

        return new ProductResponseDto.SearchAndCount(productSearchList.size(),productSearchList);
    }

    @Override
    @Transactional
    public void addProduct() {
        List<Product> all = productRepository.findAll();

        List<ProductSearch> productList = new ArrayList<>();
        for (Product products : all) {
            ProductSearch product = ProductSearch.builder()
                    .id(products.getId())
                    .productName(products.getProductName())
                    .companyName(products.getCompanyName())
                    .funcContent(products.getFuncContent())
                    .funcRawMaterials(products.getFuncRawMaterials())
                    .precautions(products.getPrecautions())
                    .importer(products.getImporter())
                    .expirationDate(products.getExpirationDate())
                    .precautionsDetails(products.getPrecautionsDetails())
                    .funcContentDetails(products.getFuncContentDetails())
                    .funcRawMaterialsDetails(products.getFuncRawMaterialsDetails())
                    .otherRawMaterials(products.getOtherRawMaterials())
                    .likeCount(products.getLikeCount())
                    .viewCount(products.getViewCount())
                    .reportNumber(products.getReportNumber())
                    .etc(products.getEtc())
                    .primaryIngredients(products.getPrimaryIngredients())
                    .customerReviewAvg(products.getCustomerReviewAvg())
                    .expertReviewAvg(products.getExpertReviewAvg())
                    .naverReview(products.getNaverReview())
                    .build();
            productList.add(product);
        }productSearchRepository.saveAll(productList);
    }

    @Override
    public ProductResponseDto.SearchAndCount findAllProductSearchListElk(String productName) {
        List<ProductResponseDto.Search> productSearchList = productSearchRepository.findByProductName(productName).stream()
                .map(ProductResponseDto.Search::new)
                .collect(Collectors.toList());

        return new ProductResponseDto.SearchAndCount(productSearchList.size(),productSearchList);
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
