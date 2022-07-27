package com.example.healthfunctionalfood.service.oauth2;

import com.example.healthfunctionalfood.dto.UserResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface OAuth2Service{

    UserResponseDto.UserInfoWithJwt kakaoLogin(UserResponseDto.KakaoUserDto kakaoUserDto);

    String getAccessTokenByCode(String code) throws JsonProcessingException;

    UserResponseDto.KakaoUserDto getUserInfoByAccessToken(String accessToken) throws JsonProcessingException;

}
