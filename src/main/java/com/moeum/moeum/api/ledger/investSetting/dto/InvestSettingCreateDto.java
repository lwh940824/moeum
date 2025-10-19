package com.moeum.moeum.api.ledger.investSetting.dto;

import jakarta.validation.constraints.NotNull;

public record InvestSettingCreateDto(
        @NotNull Long categoryId
) {}
