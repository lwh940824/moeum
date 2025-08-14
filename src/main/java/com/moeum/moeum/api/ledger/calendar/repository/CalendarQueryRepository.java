package com.moeum.moeum.api.ledger.calendar.repository;

import com.moeum.moeum.api.ledger.calendar.dto.CalendarSummaryResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface CalendarQueryRepository {

    List<CalendarSummaryResponseDto> findItemsByUserAndPeriod(Long userId, LocalDateTime startDate, LocalDateTime endDate);
}