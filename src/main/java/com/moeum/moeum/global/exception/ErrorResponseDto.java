package com.moeum.moeum.global.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

public record ErrorResponseDto(
        String code,
        String message,
        List<String> validMessage

) {
    public static ErrorResponseDto of(ErrorCode errorCode) {
        return new ErrorResponseDto(
                errorCode.name(),
                errorCode.getMessage(),
                null
        );
    }

    public static ErrorResponseDto of(ErrorCode errorCode, MethodArgumentNotValidException ex) {
        List<String> messages = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> String.format("[%s] %s", error.getField(), error.getDefaultMessage()))
                .toList();

        return new ErrorResponseDto(
                errorCode.name(),
                errorCode.getMessage(),
                messages
        );
    }
}
