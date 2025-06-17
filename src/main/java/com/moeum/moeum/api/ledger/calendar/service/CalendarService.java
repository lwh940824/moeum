package com.moeum.moeum.api.ledger.calendar.service;

import com.moeum.moeum.api.ledger.calendar.dto.CalendarSummaryRequestDto;
import com.moeum.moeum.api.ledger.calendar.dto.CalendarSummaryResponseDto;
import com.moeum.moeum.api.ledger.calendar.repository.CalendarQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarQueryRepository calendarRepository;

    public CalendarSummaryResponseDto findItemsByUserAndPeriod(Long userId, CalendarSummaryRequestDto calendarSummaryRequestDto) {
        calendarRepository.findItemsByUserAndPeriod();
    }

}