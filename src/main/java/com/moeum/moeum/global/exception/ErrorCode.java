package com.moeum.moeum.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    EXISTS_CATEGORY_GROUP(HttpStatus.CONFLICT, "이미 존재하는 카테고리 그룹입니다.");

    private final HttpStatus status;
    private final String message;
}