package com.moeum.moeum.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(MethodArgumentNotValidException ex) {
        ErrorCode error = ErrorCode.VALIDATION_ERROR;

        return ResponseEntity.status(error.getStatus())
                .body(ErrorResponseDto.of(error, ex));
    }


    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomException(CustomException customException) {
        ErrorCode errorCode = customException.getErrorCode();
        return ResponseEntity.status(errorCode.getStatus())
                .body(ErrorResponseDto.of(errorCode));
    }
}