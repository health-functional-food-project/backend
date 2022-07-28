package com.example.healthfunctionalfood.service.compare;

import com.example.healthfunctionalfood.domain.product.Product;
import com.example.healthfunctionalfood.dto.CompareResponseDto;
import com.example.healthfunctionalfood.repository.CustomExpertReviewRepository;
import com.example.healthfunctionalfood.repository.CustomProductRepository;
import com.example.healthfunctionalfood.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompareServiceImpl implements CompareService{

    private final CustomProductRepository customProductRepository;
    public final ProductRepository productRepository;
    public final CustomExpertReviewRepository customExpertReviewRepository;

    public CompareResponseDto.MainContainer getCompareProduct(Long firstProductId, Long secondProductId) {

        CompareResponseDto.ProductContainer firstProductInfo = getProductInfo(firstProductId);
        CompareResponseDto.ProductContainer secondProductInfo = getProductInfo(secondProductId);

        return CompareResponseDto.MainContainer.builder()
                .firstProductForCompare(firstProductInfo)
                .secondProductForCompare(secondProductInfo)
                .build();

    }

    public CompareResponseDto.ProductContainer getProductInfo(Long productId) {
        Product product = productRepository.findById(productId).get();

        CompareResponseDto.CompareProduct productDetail = getProductDetail(product);
        CompareResponseDto.CompareExpertReview expertReviewSummary = getExpertReviewSummary(product);

        return CompareResponseDto.ProductContainer.builder()
                .productDetail(productDetail)
                .expertReviewSummary(expertReviewSummary)
                .customerStarRatingAvg(product.getCustomerReviewAvg())
                .build();

    }

    public CompareResponseDto.CompareProduct getProductDetail(Product product) {

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

        return CompareResponseDto.CompareProduct.builder()
                .productId(product.getId())
                .companyName(product.getCompanyName())
                .productName(product.getProductName())
                .primaryIngredient(product.getPrimaryIngredients())
                .ingredientRankingList(ingredientRankingList)
                .healthConcernRankingList(healthConcernRankingList)
                .build();
    }

    public CompareResponseDto.CompareExpertReview getExpertReviewSummary(Product product) {

        TreeMap<String, Long> prosTagTree = new TreeMap<>();
        TreeMap<String, Long> consTagTree = new TreeMap<>();

        List<CompareResponseDto.prosConsTag> allProsConsTag = customExpertReviewRepository.getAllProsConsTag(product.getId());

        for (CompareResponseDto.prosConsTag prosConsTag : allProsConsTag) {
            String[] prosSplit = prosConsTag.getPros().split(",");
            String[] consSplit = prosConsTag.getCons().split(",");

            for (String pros : prosSplit) {
                consTagTree.put(pros, !prosTagTree.containsKey(pros) ? 1L : prosTagTree.get(pros) +1L);
            }
            for (String cons : consSplit) {
                prosTagTree.put(cons, !consTagTree.containsKey(cons) ? 1L : consTagTree.get(cons) + 1L);
            }
        }

        List<Map.Entry<String, Long>> prosTageEntries = prosTagTree.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).toList();
        Map<String, Long> prosTagMap = prosTageEntries.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        List<Map.Entry<String, Long>> consTagEntries = consTagTree.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).toList();
        Map<String, Long> consTagMap = consTagEntries.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));


        return CompareResponseDto.CompareExpertReview.builder()
                .starRatingAvg(product.getExpertReviewAvg())
                .prosTagMap(prosTagMap)
                .consTagMap(consTagMap)
                .build();
    }
}
