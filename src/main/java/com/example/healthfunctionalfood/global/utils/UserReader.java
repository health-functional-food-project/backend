package com.example.healthfunctionalfood.global.utils;

import com.example.healthfunctionalfood.domain.user.User;

import java.util.Optional;

public interface UserReader {

    Optional<User> findByKakaoId(String kakaoId);
}
