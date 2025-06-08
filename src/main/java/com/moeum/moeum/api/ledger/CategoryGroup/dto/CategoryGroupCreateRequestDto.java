package com.moeum.moeum.api.ledger.CategoryGroup.dto;

import com.moeum.moeum.type.CategoryType;

public record CategoryGroupCreateRequestDto(
        String name,
        CategoryType categoryType
//        String imageUrl,
)
{}
