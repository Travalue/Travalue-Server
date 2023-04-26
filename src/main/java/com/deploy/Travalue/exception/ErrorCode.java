package com.deploy.Travalue.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {
    // 400 Bad Request
    VALIDATION_EXCEPTION(HttpStatus.BAD_REQUEST, "잘못된 요청입니다"),
    NOT_FOUND_EXCEPTION(HttpStatus.BAD_REQUEST, "존재하지 않는 자원입니다"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
