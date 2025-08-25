package com.moeum.moeum.api.ledger.investSummary.dto;

public record InvestSummaryResponseDto(
        Long id,
        Long year,
        Long month,
        Long principal
) {}
