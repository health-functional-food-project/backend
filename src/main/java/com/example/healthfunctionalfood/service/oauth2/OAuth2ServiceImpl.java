package com.example.healthfunctionalfood.service.oauth2;

import com.example.healthfunctionalfood.domain.user.SocialType;
import com.example.healthfunctionalfood.domain.user.User;
import com.example.healthfunctionalfood.dto.UserResponseDto;
import com.example.healthfunctionalfood.global.jwt.JwtProvider;
import com.example.healthfunctionalfood.global.utils.UserReader;
import com.example.healthfunctionalfood.global.utils.UserStore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@PropertySource(value = {"env.properties"})
public class OAuth2ServiceImpl implements OAuth2Service {

    @Value("${secrets.KAKAO_CLIENT_ID}")
    private String clientId;
    @Value("${secrets.KAKAO_CLIENT_SECRET}")
    private String clientSecret;
    @Value("${secrets.KAKAO_REDIRECT_URI}")
    private String redirectUri;

    private final JwtProvider jwtProvider;
    private final UserReader userReader;
    private final UserStore userStore;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto.UserInfoWithJwt kakaoLogin(UserResponseDto.KakaoUserDto kakaoUserDto) {

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


    // access token 받기
    public String getAccessTokenByCode(String code) throws JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);
        body.add("client_secret", clientSecret);


        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );


        String responseBody = response.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        return jsonNode.get("access_token").asText();
    }

    public UserResponseDto.KakaoUserDto getUserInfoByAccessToken(String accessToken) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );
        String responseBody = response.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        String kakaoId = "KAKAO_" + jsonNode.get("id").asText();
        String email = jsonNode.get("kakao_account").get("email").asText();
        String gender = jsonNode.get("kakao_account").get("gender").asText();
//        String ageRange = jsonNode.get("kakao_account").get("age_range").asText();
//        String birthyear = jsonNode.get("kakao_account").get("birthyear").asText();
//        String birthday = jsonNode.get("kakao_account").get("birthday").asText();
//        String phone = jsonNode.get("kakao_account").get("phone_number").asText();

        return UserResponseDto.KakaoUserDto.builder()
                .kakaoId(kakaoId)
                .email(email)
                .gender(gender)
                .userName(null)
                .dateOfBirth(null)
                .build();
    }
}
