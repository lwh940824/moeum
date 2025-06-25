package com.moeum.moeum.api.ledger.categoryGroup.dto;

import com.moeum.moeum.type.CategoryType;
import lombok.Builder;

public record CategoryGroupResponseDto(
        Long id,
        String name,
        CategoryType categoryType,
        String imageUrl
){}