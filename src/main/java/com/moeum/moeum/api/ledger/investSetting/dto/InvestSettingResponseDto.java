package com.moeum.moeum.api.ledger.investSetting.dto;

import com.moeum.moeum.api.ledger.category.dto.CategoryResponseDto;
import com.moeum.moeum.type.YnType;

public record InvestSettingResponseDto(
        Long id,
        YnType showYn,
        YnType useYn,
        CategoryResponseDto categoryResponseDto
) {}