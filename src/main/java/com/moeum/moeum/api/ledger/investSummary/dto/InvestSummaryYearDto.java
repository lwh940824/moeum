package com.moeum.moeum.api.ledger.investSummary.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record InvestSummaryYearDto(
        Integer year,
        List<InvestSummaryMonthDto> months,
        Long totalPrincipal
) {}
