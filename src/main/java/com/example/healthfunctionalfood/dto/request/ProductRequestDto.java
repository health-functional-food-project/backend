package com.example.healthfunctionalfood.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class ProductRequestDto {

    @NoArgsConstructor
    @Getter
    public static class search{

        private String productName;
    }
}
