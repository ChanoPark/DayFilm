package com.rabbit.dayfilm.user.service;

import com.rabbit.dayfilm.common.CodeSet;
import com.rabbit.dayfilm.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public CodeSet checkNickname(String nickname) {
        if (userRepository.countUserByNickname(nickname) > 0) return CodeSet.DUPLICATE_NICKNAME;
        else return CodeSet.OK;
    }
}
