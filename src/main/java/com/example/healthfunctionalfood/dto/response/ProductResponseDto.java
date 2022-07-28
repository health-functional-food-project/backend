package com.example.healthfunctionalfood.dto.response;

import com.example.healthfunctionalfood.domain.product.Product;
import com.example.healthfunctionalfood.domain.product.ProductSearch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

public class ProductResponseDto {

    @NoArgsConstructor
    @Getter
    public static class Search{
        private Long productId;

        private String imageUrl;

        private String companyName;

        private String productName;

        private Double expertReviewAvg;

        private Double customerReviewAvg;

        public Search(Product product) {
            this.productId = product.getId();
            this.imageUrl = product.getImage().getFile_store_course();
            this.companyName = product.getCompanyName();
            this.productName = product.getProductName();
            this.expertReviewAvg = product.getExpertReviewAvg();
            this.customerReviewAvg = product.getCustomerReviewAvg();
        }

        public Search(ProductSearch productSearch) {
            this.productId = productSearch.getId();
            this.companyName = productSearch.getCompanyName();
            this.productName = productSearch.getProductName();
        }
    }

    @NoArgsConstructor
    @Getter
    @Builder
    @AllArgsConstructor
    public static class SearchAndCount{
        private Integer productCount;

        private List<Search> searchList = new ArrayList<>();
    }
}
