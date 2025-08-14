package com.moeum.moeum.api.ledger.calendar.dto;

import java.time.LocalDateTime;

public record CalendarSummaryRequestDto(
        LocalDateTime startDate,
        LocalDateTime endDate
) {}
