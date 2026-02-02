package com.moeum.moeum.api.ledger.investSetting.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record InvestSettingCreateDto(
        @NotNull Long categoryId
) {}
