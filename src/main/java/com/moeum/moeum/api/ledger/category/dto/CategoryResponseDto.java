package com.moeum.moeum.api.ledger.category.dto;

import com.moeum.moeum.type.CategoryType;
import com.moeum.moeum.type.YnType;
import lombok.Builder;

import java.util.List;

@Builder
public record CategoryResponseDto(
        Long id,
        String name,
        CategoryType categoryType,
        String imageUrl,
        YnType investmentYn,
        List<CategoryResponseDto> children
){}