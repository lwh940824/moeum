package com.moeum.moeum.api.ledger.category.dto;

import com.moeum.moeum.type.RecurringType;
import com.moeum.moeum.type.YnType;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CategoryCreateRequestDto(
        @NotBlank(message = "카테고리 이름은 필수 입력입니다.")
        @Size(max = 10, message = "카테고리 이름은 10자 이내입니다.")
        String name,
        @NotBlank
        String imageUrl,
        YnType investmentYn,
        RecurringType recurringType,
        LocalDateTime recurringStartDt,
        LocalDateTime recurringEndDt,
        Long categoryGroupId
) {}
