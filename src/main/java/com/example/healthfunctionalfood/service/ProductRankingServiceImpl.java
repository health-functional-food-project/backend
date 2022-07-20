package com.example.healthfunctionalfood.service;

import com.example.healthfunctionalfood.dto.ProductRankingResponseDto;
import com.example.healthfunctionalfood.repository.CustomCustomerReviewRepository;
import com.example.healthfunctionalfood.repository.CustomExpertReviewRepository;
import com.example.healthfunctionalfood.repository.CustomProductRepository;
import com.example.healthfunctionalfood.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductRankingServiceImpl implements ProductRankingService{

    private final UserRepository userRepository;
    private final CustomExpertReviewRepository customExpertReviewRepository;
    private final CustomCustomerReviewRepository customCustomerReviewRepository;
    private final CustomProductRepository customProductRepository;


    @Override
    public List<ProductRankingResponseDto.RankingItem> getExpertRanking(Pageable pageable) {
        List<ProductRankingResponseDto.RankingItem> expertStarRatingAvgRanking = customProductRepository.getExpertStarRatingAvgRanking(pageable);

        for (ProductRankingResponseDto.RankingItem rankingItem : expertStarRatingAvgRanking) {
            log.info("getProductName = {}", rankingItem.getProductName());
        }

        return expertStarRatingAvgRanking;
    }

    @Override
    public List<ProductRankingResponseDto.RankingItem> getIngredientsRanking(String ingredient, Pageable pageable) {
        List<ProductRankingResponseDto.RankingItem> ingredientsRanking = customProductRepository.getIngredientsRanking(ingredient, pageable);

        for (ProductRankingResponseDto.RankingItem rankingItem : ingredientsRanking) {
            log.info("getProductName = {}", rankingItem.getProductName());
        }

        return ingredientsRanking;
    }

    @Override
    public List<ProductRankingResponseDto.RankingItem> getHealthConcernRanking(String healthConcern, Pageable pageable) {
        List<ProductRankingResponseDto.RankingItem> healthConcernRanking = customProductRepository.getHealthConcernRanking(healthConcern, pageable);

        for (ProductRankingResponseDto.RankingItem rankingItem : healthConcernRanking) {
            log.info("getProductName = {}", rankingItem.getProductName());
        }

        return healthConcernRanking;
    }

    // 성분 카테고리 얻기 (stream)
    @Override
    public List<String> getIngredientsCategory() {
        List<String> ingredientsForCategoryList = customExpertReviewRepository.getIngredientsForCategory();

        List<String> dividedIngredientList = ingredientsForCategoryList.stream()
                .map(s -> s.replace(" ", ""))
                .map(s -> s.split(","))
                .flatMap(Arrays::stream).toList();

        TreeMap<String, Long> ingredientTreeMap = new TreeMap<>();
        for (String ingredient : dividedIngredientList) {
            if (ingredientTreeMap.containsKey(ingredient)) {
                ingredientTreeMap.replace(ingredient, ingredientTreeMap.get(ingredient) + 1);
            } else {
                ingredientTreeMap.put(ingredient, 1L);
            }
        }

        List<Map.Entry<String, Long>> ingredientsCategoryEntry = ingredientTreeMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).toList();
        List<String> ingredientsCategoryList = ingredientsCategoryEntry.stream().map(Map.Entry::getKey).limit(10)
                .collect(Collectors.toList());

        return ingredientsCategoryList;
    }

    // for-loop 테스트
//    @Override
//    public List<String> getIngredientsCategory2() {
//
//        List<String> IngredientsFromExpertReviewList = customExpertReviewRepository.getIngredientsFromExpertReview();
//
//        TreeMap<String, Long> ingredientTreeMap = new TreeMap<>();
//        for (String ingredient : IngredientsFromExpertReviewList) {
//            List<String> ingredientList = List.of(ingredient.split(","));
//            for (String s : ingredientList) {
//                s = s.replace(" ", "");
//
//                if (ingredientTreeMap.containsKey(s)) {
//                    ingredientTreeMap.replace(s, ingredientTreeMap.get(s) + 1);
//                } else {
//                    ingredientTreeMap.put(s, 1L);
//                }
//            }
//            }
//        List<Map.Entry<String, Long>> categories = ingredientTreeMap.entrySet().stream()
//                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).toList();
//        List<String> ingredientsCategoryList = new ArrayList<>();
//        for (int i=0; i<10; i++) {
//            ingredientsCategoryList.add(categories.get(i).getKey());
//        }
//        return ingredientsCategoryList;
//
//    }

    // 건강고민 카테고리 랭킹
    @Override
    public List<String> getHealthConcernCategory() {
        List<String> healthConcernsForCategoryList = customExpertReviewRepository.getHealthConcernsForCategory();

        List<String> dividedhealthConcernList = healthConcernsForCategoryList.stream()
                .map(s -> s.replace(" ", ""))
                .map(s -> s.split(","))
                .flatMap(Arrays::stream).toList();

        TreeMap<String, Long> healthConcernTreeMap = new TreeMap<>();
        for (String healthConcern : dividedhealthConcernList) {
            if (healthConcernTreeMap.containsKey(healthConcern)) {
                healthConcernTreeMap.replace(healthConcern, healthConcernTreeMap.get(healthConcern) + 1);
            } else {
                healthConcernTreeMap.put(healthConcern, 1L);
            }
        }

        List<Map.Entry<String, Long>> healthConcernCategoryEntry = healthConcernTreeMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).toList();
        List<String> healthConcernCategoryList = healthConcernCategoryEntry.stream().map(Map.Entry::getKey).limit(10)
                .collect(Collectors.toList());

        for (String s : healthConcernCategoryList) {
            log.info("s = {}", s);
        }
        
        return healthConcernCategoryList;
    }


    @Override
    public List<ProductRankingResponseDto.RankingItem> getRandomProductList(Pageable pageable) {
        return null;
    }


}
