package com.example.healthfunctionalfood.service.compare;

import com.example.healthfunctionalfood.domain.product.Product;
import com.example.healthfunctionalfood.dto.CompareResponseDto;
import com.example.healthfunctionalfood.repository.CustomExpertKeywordRepository;
import com.example.healthfunctionalfood.repository.CustomExpertReviewRepository;
import com.example.healthfunctionalfood.repository.CustomProductRepository;
import com.example.healthfunctionalfood.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompareServiceImpl implements CompareService{

    private final CustomProductRepository customProductRepository;
    public final ProductRepository productRepository;
    public final CustomExpertReviewRepository customExpertReviewRepository;
    private final CustomExpertKeywordRepository customExpertKeywordRepository;

    public CompareResponseDto.MainContainer getCompareProduct(Long firstProductId, Long secondProductId) {

        return null;

    }

    public CompareResponseDto.ProductContainer getProductInfo(Long productId) {


        return null;

    }

    public CompareResponseDto.CompareProduct getProductDetail(Long productId) {

        Product product = productRepository.findById(productId).get();

        // 성분에 관한 product 랭킹
        String[] ingredientArr = product.getPrimaryIngredients().split(",");
        List<CompareResponseDto.Ranking> ingredientRankingList = new ArrayList<>();
        for (String ingredient : ingredientArr) {

            if (ingredient.charAt(0) == ' ') {
                ingredient = ingredient.substring(1);
            }
            List<Long> productIdList = customProductRepository.getIngredientRank(ingredient);

            Long award = (long) productIdList.indexOf(product.getId()) + 1;
            ingredientRankingList.add(CompareResponseDto.Ranking.builder()
                            .content(ingredient)
                            .award(award)
                            .build());
        }

        // 건강 고민에 관한 product 랭킹
        List<String> healthConcernList = new ArrayList<>(List.of(product.getFuncContent().split(",")));
        if (healthConcernList.get(healthConcernList.size()-1).equals(" ")) {
            healthConcernList.remove(healthConcernList.size()-1);
        }

        List<CompareResponseDto.Ranking> healthConcernRankingList = new ArrayList<>();
        for (String healthConcern : healthConcernList) {

            if (healthConcern.charAt(0) == ' ') {
                healthConcern = healthConcern.substring(1);
            }

            List<Long> productIdList = customProductRepository.getHealthConcernRank(healthConcern);

            Long award = (long) productIdList.indexOf(product.getId()) + 1;
            healthConcernRankingList.add(CompareResponseDto.Ranking.builder()
                    .content(healthConcern)
                    .award(award)
                    .build());
        }
        CompareResponseDto.CompareProduct compareProduct = CompareResponseDto.CompareProduct.builder()
                .productId(product.getId())
                .companyName(product.getCompanyName())
                .productName(product.getProductName())
                .primaryIngredient(product.getPrimaryIngredients())
                .ingredientRankingList(ingredientRankingList)
                .healthConcernRankingList(healthConcernRankingList)
                .build();

        return compareProduct;
    }

    public CompareResponseDto.CompareExpertReview getExpertReviewSummary(Product product) {

        TreeMap<String, Long> prosTagTree = new TreeMap<>();
        TreeMap<String, Long> consTagTree = new TreeMap<>();

//        List<ExpertKeyword> expertKeyword = customExpertKeywordRepository.getExpertKeyword(product);
//        for (ExpertKeyword keyword : expertKeyword) {
//
//        }


        return null;
    }
}
