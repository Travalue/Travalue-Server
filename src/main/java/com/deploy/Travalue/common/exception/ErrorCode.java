package com.deploy.Travalue.common.exception;

import static com.deploy.Travalue.common.exception.StatusCode.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {
    VALIDATION_EXCEPTION(BAD_REQUEST, "잘못된 요청입니다"),
    ;

    private final StatusCode statusCode;
    private final String message;

    public int getStatus() {
        return statusCode.getStatus();
    }
}
