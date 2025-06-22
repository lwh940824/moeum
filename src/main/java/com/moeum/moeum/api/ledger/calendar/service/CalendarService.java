package com.moeum.moeum.api.ledger.calendar.service;

import com.moeum.moeum.api.ledger.calendar.dto.CalendarSummaryRequestDto;
import com.moeum.moeum.api.ledger.calendar.dto.CalendarSummaryResponseDto;
import com.moeum.moeum.api.ledger.calendar.repository.CalendarQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarQueryRepository calendarRepository;

    public List<CalendarSummaryResponseDto> findItemsByUserAndPeriod(Long userId, CalendarSummaryRequestDto calendarSummaryRequestDto) {
        return calendarRepository.findItemsByUserAndPeriod(userId, calendarSummaryRequestDto.startDate(), calendarSummaryRequestDto.endDate());
    }
}