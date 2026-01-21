package com.moeum.moeum.api.ledger.payment.dto;

import com.moeum.moeum.type.PaymentType;

public record PaymentResponseDto(
        Long id,
        Long parentPaymentId,
        String name,
        PaymentType paymentType
) {}
