package com.moeum.moeum.api.ledger.itemPlan.dto;

import com.moeum.moeum.type.RecurringType;

import java.time.LocalDate;

public record ItemPlanCreateRequestDto(
        Long itemId,
        RecurringType recurringType,
        LocalDate recurringStartDate,
        LocalDate recurringEndDate
) {}