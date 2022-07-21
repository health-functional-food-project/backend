package com.example.healthfunctionalfood.controller;

import com.example.healthfunctionalfood.repository.CustomProductRepositoryImpl;
import com.example.healthfunctionalfood.service.compare.CompareServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/compare")
public class CompareController {

    private final CustomProductRepositoryImpl customProductRepository;
    private final CompareServiceImpl compareService;

    @GetMapping("/test")
    public String kakaoLogin() {

//        List<Product> productList = customProductRepository.getIngredientRank("비타민 D");
//        for (Product product : productList) {
//            log.info("getProductName = {}", product.getProductName());
//        }
        compareService.getProductDetail(2L);

        return "success";
    }
}
