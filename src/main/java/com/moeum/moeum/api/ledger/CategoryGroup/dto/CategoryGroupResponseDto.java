package com.moeum.moeum.api.ledger.CategoryGroup.dto;

import com.moeum.moeum.type.CategoryType;
import lombok.Builder;

@Builder
public record CategoryGroupResponseDto(
        Long categoryGroupId,
        String name,
        CategoryType categoryType,
        String imageUrl
){}