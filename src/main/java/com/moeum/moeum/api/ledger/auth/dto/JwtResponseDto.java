package com.moeum.moeum.api.ledger.auth.dto;

import lombok.Builder;

@Builder
public record JwtResponseDto(
        String jwt
) {}
