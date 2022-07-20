package com.example.healthfunctionalfood.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class OAuth2Controller {

//    private final OAuth2Service oAuth2Service;

//    @GetMapping("/login/kakao")
//    public ResponseEntity<Success> kakaoLogin(@RequestParam("code") String code) throws JsonProcessingException {
//        log.info("code = {}", code);
//        String kakaoAccessToken = kakaoService.getAccessTokenByCode(code)
//                ;
//
//        KakaoUserDto kakaoUserDto = kakaoService.getUserInfoByAccessToken(kakaoAccessToken);
//        UserInfoWithJwtDto userInfoWithJwt = kakaoService.kakaoLogin(kakaoUserDto);
//
//        return new ResponseEntity<>(new Success("카카오 로그인 완료", userInfoWithJwt), HttpStatus.OK);
//    }
}

