package com.moeum.moeum.api.ledger.itemPlan.dto;

import com.moeum.moeum.api.ledger.category.dto.CategoryResponseDto;
import com.moeum.moeum.api.ledger.payment.dto.PaymentResponseDto;
import com.moeum.moeum.type.RecurringType;

import java.time.LocalDateTime;

public record ItemPlanResponseDto(
        Long id,
        RecurringType recurringType,
        LocalDateTime recurringStartDate,
        LocalDateTime recurringEndDate,
        CategoryResponseDto categoryResponseDto,
        PaymentResponseDto paymentResponseDto
) {}