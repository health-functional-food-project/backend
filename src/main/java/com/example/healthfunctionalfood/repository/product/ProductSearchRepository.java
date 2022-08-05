package com.example.healthfunctionalfood.repository.product;

import com.example.healthfunctionalfood.domain.product.ProductSearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface ProductSearchRepository extends ElasticsearchRepository<ProductSearch, Long> , CustomProductSearchRepository {
}
