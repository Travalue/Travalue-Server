package com.deploy.Travalue.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessCode {

    SIGNUP_SUCCESS(StatusCode.SUCCESS, "회원가입이 완료되었습니다."),
    LOGIN_SUCCESS(StatusCode.SUCCESS, "로그인이 완료되었습니다."),
    ;

    private final StatusCode statusCode;
    private final String message;

    public int getStatus(){
        return statusCode.getHttpStatusCode();
    }
}
