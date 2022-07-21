package com.example.healthfunctionalfood.controller;

import com.example.healthfunctionalfood.advice.Success;
import com.example.healthfunctionalfood.dto.request.ProductRequestDto;
import com.example.healthfunctionalfood.dto.response.ProductResponseDto;
import com.example.healthfunctionalfood.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/search")
    public ResponseEntity<Success> productSearchList(@RequestBody ProductRequestDto.search search){
        ProductResponseDto.SearchAndCount productSearchList = productService.findAllProductSearchList(search.getProductName());
        return new ResponseEntity<>(new Success("상품 조회 완료!",productSearchList), HttpStatus.OK);
    }

    @PostMapping("/{productId}/wishList")
    public ResponseEntity<Success> productWishListAdd(@PathVariable Long productId){
        productService.addProductWishList(productId);
        return new ResponseEntity<>(new Success("찜하기 성공!", ""),HttpStatus.OK);
    }
}
