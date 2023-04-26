package com.deploy.Travalue.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {
    // 400 Bad Request
    VALIDATION_EXCEPTION(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    NOT_FOUND_EXCEPTION(HttpStatus.BAD_REQUEST, "존재하지 않는 자원입니다."),
    NO_ANNOTATION_EXCEPTION(HttpStatus.BAD_REQUEST, "필요한 어노테이션이 존재하지 않습니다."),

    // 401 Unauthorized
    UNAUTHORIZED_EXCEPTION(HttpStatus.UNAUTHORIZED, "인증과정에 문제가 발생했습니다."),
    EXPIRED_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),

    // 500 Internal Server Error\
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 서버 에러가 발생했습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
