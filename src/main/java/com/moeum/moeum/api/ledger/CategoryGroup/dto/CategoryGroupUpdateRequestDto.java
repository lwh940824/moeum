package com.moeum.moeum.api.ledger.CategoryGroup.dto;

import com.moeum.moeum.type.CategoryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CategoryGroupUpdateRequestDto (
        @NotBlank @Size(max = 10) String name,
        @NotBlank CategoryType categoryType,
        @NotBlank String imageUrl
) {}