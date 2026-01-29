package com.moeum.moeum.api.ledger.itemPlan.dto;

import com.moeum.moeum.type.RecurringType;

import java.time.LocalDate;

public record ItemPlanUpdateRequestDto(
        RecurringType recurringType,
        LocalDate recurringStartDate,
        LocalDate recurringEndDate,
        Long amount,
        String memo,
        Long categoryId,
        Long paymentId
) {}
