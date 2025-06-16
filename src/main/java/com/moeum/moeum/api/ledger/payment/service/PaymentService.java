package com.moeum.moeum.api.ledger.payment.service;

import com.moeum.moeum.api.ledger.payment.dto.PaymentCreateRequestDto;
import com.moeum.moeum.api.ledger.payment.dto.PaymentResponseDto;
import com.moeum.moeum.api.ledger.payment.mapper.PaymentMapper;
import com.moeum.moeum.api.ledger.payment.repository.PaymentRepository;
import com.moeum.moeum.domain.Payment;
import com.moeum.moeum.global.exception.CustomException;
import com.moeum.moeum.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentMapper paymentMapper;
    private final PaymentRepository paymentRepository;

    public List<PaymentResponseDto> findAllByUserId(Long userId) {
        return paymentRepository.findAllByUserId(userId).stream()
                .map(paymentMapper::toDto)
                .toList();
    }

    public PaymentResponseDto findByUserIdAndId(Long userId, Long paymentId) {
        return paymentMapper.toDto(getEntity(userId, paymentId));
    }

    public PaymentResponseDto create(Long userId, PaymentCreateRequestDto paymentCreateRequestDto) {
        paymentRepository.findByUserIdAndName(userId, paymentCreateRequestDto.name())
                .ifPresent(payment -> {throw new CustomException(ErrorCode.EXISTS_PAYMENT);});

        Payment payment = paymentMapper.toEntity(paymentCreateRequestDto);

        paymentRepository.save(payment);

        return paymentMapper.toDto(payment);
    }

//    public PaymentResponseDto update(Long userId, Long paymentId) {
//        getEntity()
//    }

    public Payment getEntity(Long userId, Long paymentId) {
        return paymentRepository.findByUserIdAndId(userId, paymentId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
    }
}
