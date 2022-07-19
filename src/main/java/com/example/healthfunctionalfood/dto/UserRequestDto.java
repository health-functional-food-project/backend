package com.example.healthfunctionalfood.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class UserRequestDto {

    @Getter
    @AllArgsConstructor
    public static  class KakaoUserDto {

        private String kakaoId;

        private String email;

        private String gender;

        private String userName;

        private String dateOfBirth;
    }

}
