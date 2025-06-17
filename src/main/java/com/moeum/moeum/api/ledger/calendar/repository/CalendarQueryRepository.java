package com.moeum.moeum.api.ledger.calendar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface CalendarQueryRepository {
    findItemsByUserAndPeriod(Long userId, LocalDate startDate, LocalDate endDate);
}