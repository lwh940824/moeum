package com.moeum.moeum.api.ledger.categoryGroup.dto;

import com.moeum.moeum.type.CategoryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CategoryGroupUpdateRequestDto (
        @NotBlank(message = "카테고리 이름은 필수 입력입니다.")
        @Size(max = 10, message = "카테고리 이름은 10자 이내입니다.")
        String name,
        @NotNull
        CategoryType categoryType,
        @NotBlank
        String imageUrl
) {}