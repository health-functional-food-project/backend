package com.example.healthfunctionalfood.service;

import com.example.healthfunctionalfood.dto.UserRequestDto;
import com.example.healthfunctionalfood.dto.UserResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface OAuth2Service {

    UserResponseDto.UserInfoWithJwt kakaoLogin(UserRequestDto.KakaoUserDto kakaoUserDto);

    String getAccessTokenByCode(String code) throws JsonProcessingException;

    UserResponseDto.KakaoUserDto getUserInfoByAccessToken(String accessToken) throws JsonProcessingException;
}
