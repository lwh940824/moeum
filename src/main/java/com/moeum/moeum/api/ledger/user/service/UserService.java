package com.moeum.moeum.api.ledger.user.service;

import com.moeum.moeum.api.ledger.payment.dto.PaymentCreateRequestDto;
import com.moeum.moeum.api.ledger.payment.service.PaymentService;
import com.moeum.moeum.api.ledger.user.dto.UserResponseDto;
import com.moeum.moeum.api.ledger.user.mapper.UserMapper;
import com.moeum.moeum.api.ledger.user.repository.UserRepository;
import com.moeum.moeum.domain.User;
import com.moeum.moeum.global.exception.CustomException;
import com.moeum.moeum.global.exception.ErrorCode;
import com.moeum.moeum.type.PaymentType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PaymentService paymentService;

    public UserResponseDto getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return userMapper.toDto(user);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public User createUser(User user) {
        User saveUser = userRepository.save(user);

        //  초기 결제수단 데이터 생성
        for (PaymentType paymentType : PaymentType.values()) {
            paymentService.create(
                    saveUser.getId(),
                    PaymentCreateRequestDto.builder()
                            .parentPaymentId(null)
                            .name(paymentType.getLabel())
                            .paymentType(paymentType)
                            .build());
        }

        return saveUser;
    }

    public User getEntity(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
    }
}
