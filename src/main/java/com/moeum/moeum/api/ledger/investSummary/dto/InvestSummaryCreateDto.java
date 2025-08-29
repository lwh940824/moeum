package com.moeum.moeum.api.ledger.investSummary.dto;

import lombok.Builder;

@Builder
public record InvestSummaryCreateDto(
        Long investSettingId,
        Integer year,
        Integer month,
        Long principal
) {}
