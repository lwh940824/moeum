package com.moeum.moeum.global.exception;

import lombok.Builder;

@Builder
public record ErrorResponseDto(
        String code,
        String message

) {
    public static ErrorResponseDto of(ErrorCode errorCode) {
        return new ErrorResponseDto(
                errorCode.name(),
                errorCode.getMessage()
        );
    }
}
