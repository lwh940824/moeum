package com.moeum.moeum.api.ledger.auth.dto;

public record LoginRequestDto(
    String provider,
    String code,
    String codeVerifier, // GOOGLE만 사용
    String state // 네이버만 사용
) {}
