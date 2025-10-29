package com.moeum.moeum.api.ledger.itemPlan.dto;

import com.moeum.moeum.api.ledger.item.dto.ItemResponseDto;
import com.moeum.moeum.type.RecurringType;

import java.time.LocalDateTime;

public record ItemPlanResponseDto(
        Long id,
        RecurringType recurringType,
        LocalDateTime recurringStartDate,
        LocalDateTime recurringEndDate,
        ItemResponseDto itemResponseDto
) {}