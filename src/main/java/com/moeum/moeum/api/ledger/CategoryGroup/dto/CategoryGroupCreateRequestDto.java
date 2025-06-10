package com.moeum.moeum.api.ledger.CategoryGroup.dto;

import com.moeum.moeum.type.CategoryType;
import jakarta.validation.constraints.NotBlank;

public record CategoryGroupCreateRequestDto(
        @NotBlank String name,
        @NotBlank CategoryType categoryType
//        String imageUrl,
) {}