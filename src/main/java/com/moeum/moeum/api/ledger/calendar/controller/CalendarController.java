package com.moeum.moeum.api.ledger.calendar.controller;

import com.moeum.moeum.api.ledger.calendar.dto.CalendarSummaryRequestDto;
import com.moeum.moeum.api.ledger.calendar.dto.CalendarSummaryResponseDto;
import com.moeum.moeum.api.ledger.calendar.service.CalendarService;
import com.moeum.moeum.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/calendar")
public class CalendarController {

    private final CalendarService calendarService;

    @GetMapping
    public ResponseEntity<CalendarSummaryResponseDto> getCalendar(@AuthenticationPrincipal CustomUserDetails userDetails, CalendarSummaryRequestDto calendarSummaryRequestDto) {
        calendarService.findItemsByUserAndPeriod(userDetails.getId(), calendarSummaryRequestDto);

        return ResponseEntity.ok(CalendarSummaryResponseDto);
    }

    ;
}
