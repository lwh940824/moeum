package com.moeum.moeum.api.ledger.item.dto;

import java.time.LocalDateTime;

public record ItemCreateRequestDto(
        Long categoryId,
        Long paymentId,
        Long amount,
        LocalDateTime occurred_at,
        String memo
) {}