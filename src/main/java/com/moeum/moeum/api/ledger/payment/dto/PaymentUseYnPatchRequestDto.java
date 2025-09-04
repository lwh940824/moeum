package com.moeum.moeum.api.ledger.payment.dto;

import com.moeum.moeum.type.YnType;
import jakarta.validation.constraints.NotNull;

public record PaymentUseYnPatchRequestDto(
        @NotNull(message = "사용여부는 필수값입니다.")
        YnType useYn
) {}
