package com.example.healthfunctionalfood.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class UserDto {

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
