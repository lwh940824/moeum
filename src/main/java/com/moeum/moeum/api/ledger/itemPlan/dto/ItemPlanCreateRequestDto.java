package com.moeum.moeum.api.ledger.itemPlan.dto;

import com.moeum.moeum.type.RecurringType;

import java.time.LocalDateTime;

public record ItemPlanCreateRequestDto(
        Long itemId,
        RecurringType recurringType,
        LocalDateTime recurringStartDate,
        LocalDateTime recurringEndDate
) {}