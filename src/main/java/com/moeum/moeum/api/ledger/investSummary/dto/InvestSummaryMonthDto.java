package com.moeum.moeum.api.ledger.investSummary.dto;

import lombok.Builder;

@Builder
public record InvestSummaryMonthDto (
    Integer month,
    Long principal
) {}
