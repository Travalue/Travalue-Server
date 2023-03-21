package com.deploy.Travalue.common.exception;

import static com.deploy.Travalue.common.exception.StatusCode.SUCCESS;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessCode {

    NETWORK_SUCCESS(SUCCESS, "서버 테스트가 완료되었습니다."),
    SIGNUP_SUCCESS(SUCCESS, "회원가입이 완료되었습니다."),
    LOGIN_SUCCESS(SUCCESS, "로그인이 완료되었습니다."),
    ;

    private final StatusCode statusCode;
    private final String message;

    public int getStatus() {
        return statusCode.getStatus();
    }
}
