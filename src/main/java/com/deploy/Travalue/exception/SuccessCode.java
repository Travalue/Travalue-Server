package com.deploy.Travalue.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessCode {

    SIGNUP_SUCCESS(HttpStatus.OK, "회원가입이 완료되었습니다."),
    LOGIN_SUCCESS(HttpStatus.OK, "로그인이 완료되었습니다."),

    // user
    NICKNAME_SUCCESS(HttpStatus.OK, "닉네임 업데이트가 완료되었습니다."),
    CHECK_NICKNAME_SUCCESS(HttpStatus.OK, "닉네임 중복 검사가 완료되었습니다."),
    CREATE_MY_TRIP_SUCCESS(HttpStatus.CREATED, "나의 여행지가 생성되었습니다."),
    GET_MY_TRIP_LIST_SUCCESS(HttpStatus.OK, "나의 여행지 리스트 조회성공"),
    GET_MY_PAGE_SUCCESS(HttpStatus.OK, "마이 페이지 조회성공"),

    // travel
    CREATE_CATEGORY_SUCCESS(HttpStatus.CREATED, "카테고리가 생성됐습니다."),

    READ_TRAILERS_SUCCESS(HttpStatus.OK, "트레일러 전체 조회 성공"),
    READ_TRAVELLERS_SUCCESS(HttpStatus.OK, "트레블러 전체 조회 성공"),
    ;

    private final HttpStatus httpStatus;
    private final String message;

}
