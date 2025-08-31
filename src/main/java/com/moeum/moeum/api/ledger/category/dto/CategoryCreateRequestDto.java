package com.moeum.moeum.api.ledger.category.dto;

import com.moeum.moeum.type.CategoryType;
import com.moeum.moeum.type.RecurringType;
import com.moeum.moeum.type.YnType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record CategoryCreateRequestDto(
        Long groupId,

        @NotBlank(message = "카테고리명은 필수 입력입니다.")
        @Size(max = 10, message = "카테고리명은 10자 이내입니다.")
        String name,

        @NotNull
        CategoryType categoryType,

        @NotBlank
        String imageUrl,

        @NotNull
        YnType investmentYn,

        @NotNull
        YnType useYn
) {
}