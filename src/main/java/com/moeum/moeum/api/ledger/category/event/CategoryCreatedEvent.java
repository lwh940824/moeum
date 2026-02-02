package com.moeum.moeum.api.ledger.category.event;

import lombok.Builder;

@Builder
public record CategoryCreatedEvent(
        Long userId,
        Long categoryId
) {}
