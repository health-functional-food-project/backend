package com.example.healthfunctionalfood.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class UserResponseDto {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class UserInfoWithJwt {

        private String accessToken;

        private String kakaoId;

        private String email;

        private String gender;

        private String userName;

        private String nickname;

        private Integer ageGroup;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class KakaoUserDto {

        private String kakaoId;

        private String email;

        private String gender;

        private String userName;

        private String dateOfBirth;
    }
}
