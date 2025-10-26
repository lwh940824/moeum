package com.moeum.moeum.api.ledger.category.dto;

import com.moeum.moeum.type.CategoryType;

public record CategoryParentResponseDto(
        Long id,
        String name,
        CategoryType categoryType,
        String imageUrl
) {}
