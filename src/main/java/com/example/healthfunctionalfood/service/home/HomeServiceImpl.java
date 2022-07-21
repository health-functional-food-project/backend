package com.example.healthfunctionalfood.service.home;

import com.example.healthfunctionalfood.dto.HomeResponseDto;
import com.example.healthfunctionalfood.dto.ProductRankingResponseDto;
import com.example.healthfunctionalfood.repository.CustomCustomerReviewRepository;
import com.example.healthfunctionalfood.repository.CustomExpertReviewRepository;
import com.example.healthfunctionalfood.repository.CustomProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {

    private final CustomExpertReviewRepository customExpertReviewRepository;
    private final CustomProductRepository customProductRepository;
    private final CustomCustomerReviewRepository customCustomerReviewRepository;

    @Override
    public HomeResponseDto.MainContainer getHomeMain(Pageable pageable) {

        // 약사 추천
        List<Long> productIdList = Arrays.asList(1L, 2L, 3L, 4L, 5L);

        List<HomeResponseDto.ExpertRecommend> expertRecommendList = new ArrayList<>();



        // 전문가 리뷰
        List<HomeResponseDto.ExpertReview> expertReviewList = customExpertReviewRepository.getExpertReviewForMain();
        for (HomeResponseDto.ExpertReview expertReview : expertReviewList) {
            log.info("getProductName = {}", expertReview.getProductName());
//            log.info("getExpertCount = {}", expertReview.getExpertCount());
            log.info("getComment = {}", expertReview.getComment());
        }

        // 인기 건기식 행킹 카테고리
        List<String> ingredientsForCategoryList = customExpertReviewRepository.getIngredientsForCategory();
        List<String> dividedIngredientList = ingredientsForCategoryList.stream()
                .map(s -> s.replace(" ", ""))
                .map(s -> s.split(","))
                .flatMap(Arrays::stream).toList();

        List<String> healthConcernsForCategoryList = customExpertReviewRepository.getHealthConcernsForCategory();

        List<String> dividedhealthConcernList = healthConcernsForCategoryList.stream()
                .map(s -> s.replace(" ", ""))
                .map(s -> s.split(","))
                .flatMap(Arrays::stream).toList();

        TreeMap<String, Long> categoryTreeMap = new TreeMap<>();
        for (String healthConcern : dividedhealthConcernList) {
            if (categoryTreeMap.containsKey(healthConcern)) {
                categoryTreeMap.replace(healthConcern, categoryTreeMap.get(healthConcern) + 1);
            } else {
                categoryTreeMap.put(healthConcern, 1L);
            }
        }
        for (String ingredient : dividedIngredientList) {
            if (categoryTreeMap.containsKey(ingredient)) {
                categoryTreeMap.replace(ingredient, categoryTreeMap.get(ingredient) + 1);
            } else {
                categoryTreeMap.put(ingredient, 1L);
            }
        }

        List<Map.Entry<String, Long>> categoryEntry = categoryTreeMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).toList();
        List<String> categoryList = categoryEntry.stream().map(Map.Entry::getKey).limit(10).toList();

        // 인기건기식 랭킹
        List<ProductRankingResponseDto.RankingItem> productRankingList = customProductRepository.getIngredientsRanking(categoryList.get(0));
        if (productRankingList.isEmpty()) {
            productRankingList = customProductRepository.getHealthConcernRanking(categoryList.get(0));
        }

        HomeResponseDto.PopularProductRanking popularProductRanking = HomeResponseDto.PopularProductRanking.builder()
                .categoryList(categoryList)
                .productList(productRankingList)
                .build();

        // 금주의 성분 트렌드
        List<String> ingredientListOfWeek = Arrays.asList("오메가3", "프로바이오틱스", "홍삼", "밀크시슬", "아르기닌", "오메가3", "프로바이오틱스", "홍삼", "밀크시슬", "아르기닌");
        LocalDate asOfDate = LocalDate.now();
        HomeResponseDto.IngredientTrend ingredientTrend = HomeResponseDto.IngredientTrend.builder()
                .asOfDate(asOfDate)
                .ingredientList(ingredientListOfWeek)
                .build();



        return null;
    }
}
