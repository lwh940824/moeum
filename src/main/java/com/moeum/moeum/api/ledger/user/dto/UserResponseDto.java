package com.moeum.moeum.api.ledger.user.dto;

public record UserResponseDto(
        String email,
        String name,
        String address,
        String provider
) {}
