package com.moeum.moeum.api.ledger.payment.dto;

import com.moeum.moeum.type.PaymentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record PaymentCreateRequestDto(
        Long parentPaymentId,
        @NotBlank(message = "결제수단 이름은 필수 입력입니다.")
        @Size(max = 10, message = "결제수단 이름은 10자 이내입니다.")
        String name,
        PaymentType paymentType
) {}
