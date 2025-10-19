package com.moeum.moeum.api.ledger.item.dto;

import com.moeum.moeum.type.PaymentType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ItemResponseDto(
        Long itemId,
        Long amount,
        LocalDateTime occurredAt,
        String memo,
        ItemCategoryDto childCategory,
        ItemCategoryDto parentCategory,
        ItemPaymentDto payment
) {
    @Builder
    public record ItemCategoryDto(Long categoryId, String name, String imageUrl) {}
    @Builder
    public record ItemPaymentDto(Long paymentId, String name, PaymentType paymentType) {}
}
