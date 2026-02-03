package com.moeum.moeum.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "유효하지 않은 요청 값 입니다."),

    // 인증
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."),
    NOT_SUPPORT_LOGIN(HttpStatus.BAD_REQUEST, "지원하지 않는 로그인 방식입니다."),
    INVALID_LOGIN(HttpStatus.BAD_REQUEST, "유효하지 않은 로그인입니다."),

//    EXISTS_CATEGORY(HttpStatus.CONFLICT, "이미 존재하는 카테고리 입니다."),
//    NOT_FOUND_CATEGORY(HttpStatus.NOT_FOUND, "존재하지 않는 카테고리입니다."),
//    REQUIRED_CATEGORY_GROUP(HttpStatus.BAD_REQUEST, "카테고리 그룹은 필수입니다."),

    // 카테고리
    EXISTS_CATEGORY(HttpStatus.CONFLICT, "이미 존재하는 카테고리입니다."),
    NOT_FOUND_CATEGORY(HttpStatus.NOT_FOUND, "존재하지 않는 카테고리입니다."),
    REQUIRED_CATEGORY(HttpStatus.BAD_REQUEST, "카테고리는 필수입니다."),
    CATEGORY_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "카테고리 관련 작업중 오류 발생"),
    EXISTS_CATEGORY_GROUP(HttpStatus.CONFLICT, "이미 상위 카테고리를 가진 카테고리입니다."),

    // 아이템
    NOT_FOUND_ITEM(HttpStatus.NOT_FOUND, "존재하지 않는 아이템입니다."),
    PARENT_CATEGORY_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "아이템은 하위 카테고리에만 등록할 수 있습니다."),

    // 결제 수단
    EXISTS_PAYMENT(HttpStatus.CONFLICT, "이미 존재하는 결제수단입니다."),
    NOT_FOUND_PAYMENT(HttpStatus.NOT_FOUND, "존재하지 않는 결제수단입니다."),
    REQUIRED_PAYMENT(HttpStatus.BAD_REQUEST, "결제수단은 필수입니다."),
    EXISTS_PAYMENT_GROUP(HttpStatus.CONFLICT, "이미 상위 카테고리를 가진 결제수단입니다."),
    BASE_PAYMENT(HttpStatus.CONFLICT, "기본 결제수단은 수정할수 없습니다."),

    // 집계 설정
    EXISTS_INVEST_SETTING(HttpStatus.CONFLICT, "이미 존재하는 집계 설정입니다."),
    NOT_FOUND_INVEST_SETTING(HttpStatus.NOT_FOUND, "존재하지 않는 집계 설정입니다."),

    // 반복 설정
    NOT_FOUND_ITEM_PLAN(HttpStatus.NOT_FOUND, "존재하지 않는 반복 설정입니다."),

    // MinIO 관련 에러
    FILE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드중 문제가 발생했습니다."),
    NOT_FOUND_BUCKET(HttpStatus.NOT_FOUND, "존재하지 않는 버킷입니다."),

    // Zip 관련 에러
    UNZIP_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "ZIP파일 작업중 문제가 발생했습니다."),
    FILE_UPLOAD_TYPE_ERROR(HttpStatus.BAD_REQUEST, "아이콘 이미지만 업로드 가능합니다."),

    // 날짜 관련 에러
    INVALID_DATE_RANGE(HttpStatus.BAD_REQUEST, "시작 날짜가 종료 날짜보다 늦을 수 없습니다."),

    // 공통
    INVALID_AMOUNT(HttpStatus.BAD_REQUEST, "금액은 0 보다 작을수 없습니다.")
    ;

    private final HttpStatus status;
    private final String message;
}