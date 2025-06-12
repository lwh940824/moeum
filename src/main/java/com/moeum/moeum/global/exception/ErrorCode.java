package com.moeum.moeum.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "유효하지 않은 요청 값 입니다."),

    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."),

    EXISTS_CATEGORY(HttpStatus.CONFLICT, "이미 존재하는 카테고리 입니다."),
    NOT_FOUND_CATEGORY(HttpStatus.NOT_FOUND, "존재하지 않는 카테고리입니다."),

    EXISTS_CATEGORY_GROUP(HttpStatus.CONFLICT, "이미 존재하는 카테고리 그룹입니다."),
    NOT_FOUND_CATEGORY_GROUP(HttpStatus.NOT_FOUND, "존재하지 않는 카테고리 그룹입니다."),;


    private final HttpStatus status;
    private final String message;
}