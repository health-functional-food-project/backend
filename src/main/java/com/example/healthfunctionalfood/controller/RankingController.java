package com.example.healthfunctionalfood.controller;

import com.example.healthfunctionalfood.dto.ProductRankingResponseDto;
import com.example.healthfunctionalfood.service.ranking.ProductRankingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/ranking")
public class RankingController {

    private final ProductRankingService productRankingService;

    // 랭킹 홈
    @GetMapping("/home")
    public ProductRankingResponseDto.RankingHomeContainer getRankingHome(Pageable pageable) {
        return productRankingService.getRankingHome(pageable);
    }

    // 전문가 리뷰 랭킹 페이징
    @GetMapping("/expert-review")
    public String getExpertRanking(Pageable pageable) {
        productRankingService.getExpertRanking(pageable);
        return "success";
    }

    // 성분 랭킹 페이징
    @GetMapping("/ingredient")
    public String getIngredientsRanking(@RequestParam("ingredient") String ingredient, Pageable pageable) {
        productRankingService.getIngredientsRanking(ingredient, pageable);
        return "success";
    }

    // 건강 고민 페이징
    @GetMapping("/health-concern")
    public String getHealthConcernRanking(@RequestParam("health-concern") String healthConcern, Pageable pageable) {
        productRankingService.getHealthConcernRanking(healthConcern, pageable);
        return "success";
    }

    // 랜덤 건강기능식품 페이징
    @GetMapping("/random")
    private List<ProductRankingResponseDto.RankingItem> getRandomProduct(Pageable pageable) {
        return productRankingService.getRandomProduct(pageable);
    }
}
