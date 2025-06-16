package com.moeum.moeum.api.ledger.user.service;

import com.moeum.moeum.api.ledger.user.dto.UserResponseDto;
import com.moeum.moeum.api.ledger.user.mapper.UserMapper;
import com.moeum.moeum.api.ledger.user.repository.UserRepository;
import com.moeum.moeum.domain.User;
import com.moeum.moeum.global.exception.CustomException;
import com.moeum.moeum.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserResponseDto getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return userMapper.toDto(user);
    }

    public User getEntity(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
    }
}
