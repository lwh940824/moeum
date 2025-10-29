package com.moeum.moeum.api.ledger.payment.service;

import com.moeum.moeum.api.ledger.payment.dto.PaymentCreateRequestDto;
import com.moeum.moeum.api.ledger.payment.dto.PaymentResponseDto;
import com.moeum.moeum.api.ledger.payment.dto.PaymentUpdateRequestDto;
import com.moeum.moeum.api.ledger.payment.dto.PaymentUseYnPatchRequestDto;
import com.moeum.moeum.api.ledger.payment.mapper.PaymentMapper;
import com.moeum.moeum.api.ledger.payment.repository.PaymentRepository;
import com.moeum.moeum.api.ledger.user.service.UserService;
import com.moeum.moeum.domain.Payment;
import com.moeum.moeum.global.exception.CustomException;
import com.moeum.moeum.global.exception.ErrorCode;
import com.moeum.moeum.type.YnType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentMapper paymentMapper;
    private final PaymentRepository paymentRepository;
    private final UserService userService;

    @Transactional(readOnly = true)
    public List<PaymentResponseDto> findAllByUserId(Long userId) {
        return paymentRepository.findAllByUserId(userId).stream()
                .map(paymentMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public PaymentResponseDto findByUserIdAndId(Long userId, Long paymentId) {
        return paymentMapper.toDto(getEntity(userId, paymentId));
    }

    @Transactional
    public PaymentResponseDto create(Long userId, PaymentCreateRequestDto paymentCreateRequestDto) {
        Optional<Payment> existPayment = paymentRepository.findByUserIdAndName(userId, paymentCreateRequestDto.name());

        // 이미 생성되었던 결제수단이라면 사용 활성화만
        if (existPayment.isPresent()) {
            Payment payment = existPayment.get();
            payment.changeUseYn(YnType.Y);
            return paymentMapper.toDto(payment);
        }

        Payment payment = paymentMapper.toEntity(paymentCreateRequestDto);
        payment.assignUser(userService.getEntity(userId));

        paymentRepository.save(payment);

        return paymentMapper.toDto(payment);
    }

    @Transactional
    public PaymentResponseDto update(Long userId, Long paymentId, PaymentUpdateRequestDto paymentUpdateRequestDto) {
        Payment payment = getEntity(userId, paymentId);

        if (!payment.getName().equals(paymentUpdateRequestDto.name())) {
            paymentRepository.findByUserIdAndName(userId, paymentUpdateRequestDto.name())
                    .ifPresent(entity -> {throw new CustomException(ErrorCode.EXISTS_PAYMENT);});
        }

        payment.update(paymentUpdateRequestDto.name(), paymentUpdateRequestDto.paymentType());

        return paymentMapper.toDto(payment);
    }

    @Transactional
    public void changeUseYn(Long userId, Long paymentId, @RequestBody @Valid PaymentUseYnPatchRequestDto paymentUseYnPatchRequestDto) {
        Payment payment = getEntity(userId, paymentId);

        // 다른 경우만 update
        if (!payment.getUseYn().equals(paymentUseYnPatchRequestDto.useYn())) {
            payment.changeUseYn(paymentUseYnPatchRequestDto.useYn());
        }
    }

    public Payment getEntity(Long userId, Long paymentId) {
        return paymentRepository.findByUserIdAndId(userId, paymentId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
    }
}
