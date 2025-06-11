package com.moeum.moeum.api.ledger.User.dto;

public record UserResponseDto(
        String email,
        String name,
        String address,
        String provider
) {}
