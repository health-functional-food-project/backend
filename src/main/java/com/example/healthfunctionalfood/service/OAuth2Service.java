package com.example.healthfunctionalfood.service;

import com.example.healthfunctionalfood.domain.user.SocialType;
import com.example.healthfunctionalfood.domain.user.User;
import com.example.healthfunctionalfood.dto.UserRequestDto;
import com.example.healthfunctionalfood.dto.UserResponseDto;
import com.example.healthfunctionalfood.global.jwt.JwtProvider;
import com.example.healthfunctionalfood.global.utils.UserReader;
import com.example.healthfunctionalfood.global.utils.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class OAuth2Service {

    @Value("dwq")
    private String clientId;
    @Value("dqw")
    private String clientSecret;
    @Value("https://www.yagiyagi.co.kr/oauth2/callback/kakao")
    private String redirectUrl;

    private final JwtProvider jwtProvider;
    private final UserReader userReader;
    private final UserStore userStore;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto.UserInfoWithJwt kakaoLogin(UserRequestDto.KakaoUserDto kakaoUserDto) {

        Random random = new Random();
        Optional<User> userOptional = userReader.findByKakaoId(kakaoUserDto.getKakaoId());

        User savedUser;
        if (userOptional.isPresent()) {
            savedUser = userOptional.get();
        } else {
            String password = passwordEncoder.encode("password");
            savedUser = User.OauthSignUp(
                    kakaoUserDto.getKakaoId(),
                    kakaoUserDto.getEmail(),
                    password,
                    kakaoUserDto.getUserName(),
                    null,
                    kakaoUserDto.getDateOfBirth(),
                    kakaoUserDto.getGender(),
                    null,
                    null,
                    SocialType.KAKAO);
            userStore.store(savedUser);
        }

        UserResponseDto.KakaoUserDto kakaoUserDtoForJwtToken = UserResponseDto.KakaoUserDto.builder()
                .kakaoId(savedUser.getKakaoId())
                .userName(savedUser.getUserName())
                .dateOfBirth(String.valueOf(savedUser.getAge()))
                .userName(savedUser.getUserName())
                .gender(savedUser.getGender())
                .build();
        String accessToken = jwtProvider.createToken(kakaoUserDtoForJwtToken);

        return UserResponseDto.UserInfoWithJwt.builder()
                .accessToken(accessToken)
                .kakaoId(savedUser.getKakaoId())
                .ageGroup(savedUser.getAge())
                .email(savedUser.getEmail())
                .gender(savedUser.getGender())
                .nickname(savedUser.getNickname())
                .userName(savedUser.getUserName())
                .build();
    }

}
