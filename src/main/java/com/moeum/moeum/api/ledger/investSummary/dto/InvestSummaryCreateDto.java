package com.moeum.moeum.api.ledger.investSummary.dto;

public record InvestSummaryCreateDto(
        Long year,
        Long month,
        Long principal
) {}
