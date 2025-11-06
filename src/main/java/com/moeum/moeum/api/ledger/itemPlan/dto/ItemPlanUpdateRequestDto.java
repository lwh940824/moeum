package com.moeum.moeum.api.ledger.itemPlan.dto;

import com.moeum.moeum.type.RecurringType;

import java.time.LocalDateTime;

public record ItemPlanUpdateRequestDto(
        RecurringType recurringType,
        LocalDateTime recurringStartDate,
        LocalDateTime recurringEndDate,
        Long amount,
        String memo,
        Long categoryId,
        Long paymentId
) {}
