package com.moeum.moeum.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomException(CustomException customException) {
        ErrorCode errorCode = customException.getErrorCode();
        return ResponseEntity.status(errorCode.getStatus())
                .body(ErrorResponseDto.of(errorCode));
    }
}