package com.moeum.moeum.api.ledger.payment.dto;

import com.moeum.moeum.type.PaymentType;

public record PaymentResponseDto(
        Long paymentId,
        String name,
        PaymentType paymentType
) {}
