package com.moeum.moeum.api.ledger.calendar.dto;

import java.time.LocalDateTime;

public record CalendarSummaryResponseDto(
        Long itemId,
        Long categoryGroupId,
        String categoryGroupName,
        Long categoryId,
        String categoryName,
        LocalDateTime date,
        Long amount,
        String memo
) {}