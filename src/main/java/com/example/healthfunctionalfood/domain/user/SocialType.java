package com.example.healthfunctionalfood.domain.user;

import lombok.Getter;

import java.util.Map;
import java.util.function.Function;

@Getter
public enum SocialType {
    KAKAO("KAKAO", KakaoOAuth2UserInfo::new);

    private final String socialType;
    private final Function<Map<String, Object>, OAuth2UserInfo> oAuth2UserInfoFactory;

    SocialType(String socialType,
               Function<Map<String, Object>, OAuth2UserInfo> oAuth2UserInfoFactory) {
        this.socialType = socialType;
        this.oAuth2UserInfoFactory = oAuth2UserInfoFactory;
    }


    public OAuth2UserInfo getOauth2UserInfo(
            Map<String, Object> attributes) {
        return getOAuth2UserInfoFactory().apply(attributes);
    }
}
