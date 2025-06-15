package com.moeum.moeum.api.ledger.item.dto;

import com.moeum.moeum.domain.Payment;

import java.time.LocalDateTime;

public record ItemCreateRequestDto(
        Long amount,
        LocalDateTime occurred_at,
        Long categoryId,
        Payment payment
) {}