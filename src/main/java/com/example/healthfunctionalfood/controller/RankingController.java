package com.example.healthfunctionalfood.controller;

import com.example.healthfunctionalfood.service.ProductRankingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/ranking")
public class RankingController {

    private final ProductRankingService productRankingService;

    @GetMapping("/expert-review")
    public String getExpertRanking(Pageable pageable) {
        productRankingService.getExpertRanking(pageable);
        return "success";
    }

    @GetMapping("/ingredient")
    public String getIngredientsRanking(@RequestParam("ingredient") String ingredient, Pageable pageable) {
        productRankingService.getIngredientsRanking(ingredient, pageable);
        return "success";
    }

    @GetMapping("/health-concern")
    public String getHealthConcernRanking(@RequestParam("health-concern") String healthConcern, Pageable pageable) {
        productRankingService.getHealthConcernRanking(healthConcern, pageable);
        return "success";
    }

    @GetMapping("/test")
    public String getTest() {
        return "success";
    }
}
