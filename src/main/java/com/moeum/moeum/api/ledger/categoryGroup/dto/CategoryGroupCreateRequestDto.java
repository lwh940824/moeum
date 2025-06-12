package com.moeum.moeum.api.ledger.categoryGroup.dto;

import com.moeum.moeum.type.CategoryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryGroupCreateRequestDto(
        @NotBlank(message = "카테고리 이")
        @Size(max = 10, message = "카테고리 이름은 10자 이내입니다.")
        String name,
        @NotBlank
        CategoryType categoryType,
        @NotBlank
        String imageUrl
) {}