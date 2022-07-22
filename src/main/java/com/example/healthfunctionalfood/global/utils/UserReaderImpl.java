package com.example.healthfunctionalfood.global.utils;

import com.example.healthfunctionalfood.domain.user.User;
import com.example.healthfunctionalfood.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserReaderImpl implements UserReader{

    private final UserRepository userRepository;

    @Override
    public Optional<User> findByKakaoId(String kakaoId) {
        return userRepository.findByKakaoId(kakaoId);
    }
}
