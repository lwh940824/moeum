package com.moeum.moeum.api.ledger.category.dto;

import com.moeum.moeum.type.CategoryType;

public record CategoryResponseDto(
        Long id,
        Long parentId,
        String name,
        CategoryType categoryType,
        String imageUrl
){}