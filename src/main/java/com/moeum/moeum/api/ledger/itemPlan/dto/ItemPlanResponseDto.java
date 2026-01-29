package com.moeum.moeum.api.ledger.itemPlan.dto;

import com.moeum.moeum.api.ledger.category.dto.CategoryResponseDto;
import com.moeum.moeum.api.ledger.payment.dto.PaymentResponseDto;
import com.moeum.moeum.type.RecurringType;

import java.time.LocalDate;

public record ItemPlanResponseDto(
        Long id,
        RecurringType recurringType,
        LocalDate recurringStartDate,
        LocalDate recurringEndDate,
        CategoryResponseDto categoryResponseDto,
        PaymentResponseDto paymentResponseDto
) {}
