package com.moeum.moeum.api.ledger.item.dto;

public record ItemToSummaryDto(
        Integer year,
        Integer month,
        Long principal
) {}
