package com.moeum.moeum.api.ledger.item.dto;

public record ItemToSummaryDto(
        Long year,
        Long month,
        Long principal
) {}
