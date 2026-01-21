package com.moeum.moeum.api.ledger.payment.service;

import com.moeum.moeum.api.ledger.payment.dto.PaymentCreateRequestDto;
import com.moeum.moeum.api.ledger.payment.dto.PaymentResponseDto;
import com.moeum.moeum.api.ledger.payment.dto.PaymentUpdateRequestDto;
import com.moeum.moeum.api.ledger.payment.dto.PaymentUseYnPatchRequestDto;
import com.moeum.moeum.api.ledger.payment.mapper.PaymentMapper;
import com.moeum.moeum.api.ledger.payment.repository.PaymentRepository;
import com.moeum.moeum.api.ledger.user.repository.UserRepository;
import com.moeum.moeum.domain.Payment;
import com.moeum.moeum.global.exception.CustomException;
import com.moeum.moeum.global.exception.ErrorCode;
import com.moeum.moeum.type.PaymentType;
import com.moeum.moeum.type.YnType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentMapper paymentMapper;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<PaymentResponseDto> getPaymentList(Long userId) {
        return paymentRepository.findAllByUserId(userId).stream()
                .map(paymentMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public PaymentResponseDto getPayment(Long userId, Long paymentId) {
        return paymentMapper.toDto(getEntity(userId, paymentId));
    }

    @Transactional
    public PaymentResponseDto create(Long userId, PaymentCreateRequestDto paymentCreateRequestDto) {
        Optional<Payment> existPayment = paymentRepository.findByUserIdAndName(userId, paymentCreateRequestDto.name());

        // 이미 생성됐던 결제수단이라면 사용 활성화만
        if (existPayment.isPresent()) {
            Payment payment = existPayment.get();
            payment.changeUseYn(YnType.Y);
            return paymentMapper.toDto(payment);
        }

        Payment payment = paymentMapper.toEntity(paymentCreateRequestDto);
        payment.assignUser(userRepository.getReferenceById(userId));

        // 상위 결제수단 설정
        if (paymentCreateRequestDto.parentPaymentId() != null) {
            Payment parentPayment = getEntity(userId, paymentCreateRequestDto.parentPaymentId());

            if (parentPayment.getParentPayment() != null) {
                throw new CustomException(ErrorCode.EXISTS_PAYMENT_GROUP);
            }

            payment.changeParentPayment(parentPayment);
        }

        paymentRepository.save(payment);

        return paymentMapper.toDto(payment);
    }

    @Transactional
    public PaymentResponseDto update(Long userId, Long paymentId, PaymentUpdateRequestDto paymentUpdateRequestDto) {
        Payment payment = getEntity(userId, paymentId);

        // 사용자가 이미 등록한 결제수단
        if (!payment.getName().equals(paymentUpdateRequestDto.name())) {
            paymentRepository.findByUserIdAndName(userId, paymentUpdateRequestDto.name())
                    .ifPresent(entity -> {throw new CustomException(ErrorCode.EXISTS_PAYMENT);});
        }

        // 기본 결제수단 수정 요청시
        if (isBasePayment(payment)) {
            throw new CustomException(ErrorCode.BASE_PAYMENT);
        }

        // 상위 결제수단 설정
        if (paymentUpdateRequestDto.parentPaymentId() == null) {
            payment.changeParentPayment(null);
        } else {
            Payment parentPayment = getEntity(userId, paymentUpdateRequestDto.parentPaymentId());

            // 자기 자신 설정 금지
            if (parentPayment.getParentPayment() != null || parentPayment.getId().equals(payment.getId())) {
                throw new CustomException(ErrorCode.EXISTS_PAYMENT_GROUP);
            }

            payment.changeParentPayment(parentPayment);
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

    public boolean isBasePayment(Payment payment) {
        return Arrays.stream(PaymentType.values()).anyMatch(paymentType -> paymentType.getLabel().equals(payment.getName()));
    }

    public Payment getEntity(Long userId, Long paymentId) {
        return paymentRepository.findByUserIdAndId(userId, paymentId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PAYMENT));
    }
}
