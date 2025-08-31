package com.moeum.moeum.api.ledger.investSummary.dto;

public record InvestSummaryResponseDto(
        Long id,
        Integer year,
        Integer month,
        Long principal
) {}
