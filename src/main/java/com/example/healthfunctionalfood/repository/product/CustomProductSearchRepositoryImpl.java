package com.example.healthfunctionalfood.repository.product;

import com.example.healthfunctionalfood.domain.product.ProductSearch;
import com.example.healthfunctionalfood.repository.CustomProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class CustomProductSearchRepositoryImpl implements CustomProductSearchRepository {

    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<ProductSearch> findByProductName(String search) {
        Criteria criteria = Criteria.where("productName").contains(search);

        CriteriaQuery query = new CriteriaQuery(criteria);
        SearchHits<ProductSearch> productSearchHits = elasticsearchOperations.search(query, ProductSearch.class);

        return productSearchHits.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }
}
