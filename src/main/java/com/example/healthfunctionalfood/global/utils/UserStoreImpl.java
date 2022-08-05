package com.example.healthfunctionalfood.global.utils;

import com.example.healthfunctionalfood.domain.user.User;
import com.example.healthfunctionalfood.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserStoreImpl implements UserStore{

    private final UserRepository userRepository;

    @Override
    public User store(User user) {
        return userRepository.save(user);
    }
}
