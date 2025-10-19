package com.moeum.moeum.api.ledger.item.dto;

import java.time.LocalDateTime;

public record ItemUpdateRequestDto(
        Long amount,
        LocalDateTime occurredAt,
        String memo,
        Long categoryId,
        Long paymentId
) {}
