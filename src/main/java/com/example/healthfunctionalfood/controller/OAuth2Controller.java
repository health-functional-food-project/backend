package com.example.healthfunctionalfood.controller;

import com.example.healthfunctionalfood.advice.Success;
import com.example.healthfunctionalfood.dto.UserResponseDto;
import com.example.healthfunctionalfood.service.OAuth2Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class OAuth2Controller {

    private final OAuth2Service oAuth2Service;

    //인가 코드 받는 주소 : https://kauth.kakao.com/oauth/authorize/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code
    @GetMapping("/login/kakao")
    public ResponseEntity<Success> kakaoLogin(@RequestParam("code") String code) throws JsonProcessingException, JsonProcessingException {
        log.info("code = {}", code);
        String kakaoAccessToken = oAuth2Service.getAccessTokenByCode(code);

        UserResponseDto.KakaoUserDto kakaoUserDto = oAuth2Service.getUserInfoByAccessToken(kakaoAccessToken);
        UserResponseDto.UserInfoWithJwt userInfoWithJwt = oAuth2Service.kakaoLogin(kakaoUserDto);

        return new ResponseEntity<>(new Success("카카오 로그인 완료", userInfoWithJwt), HttpStatus.OK);
    }

}

