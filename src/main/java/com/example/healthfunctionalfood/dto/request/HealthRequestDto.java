package com.example.healthfunctionalfood.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class HealthRequestDto {

    @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    public static class userInfo{
        private String userName;
        private String birthDate;
        private String userCellphoneNumber;
    }
}
