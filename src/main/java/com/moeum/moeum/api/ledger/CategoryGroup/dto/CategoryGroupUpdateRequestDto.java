package com.moeum.moeum.api.ledger.CategoryGroup.dto;

import com.moeum.moeum.type.CategoryType;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CategoryGroupUpdateRequestDto (
        @NotBlank String name,
        @NotBlank CategoryType categoryType,
        @NotBlank String imageUrl
) {}