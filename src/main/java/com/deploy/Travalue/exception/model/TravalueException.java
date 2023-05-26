package com.deploy.Travalue.exception.model;

import com.deploy.Travalue.exception.ErrorCode;
import lombok.Getter;

@Getter
public class TravalueException extends RuntimeException {
    private final ErrorCode errorCode;

    public TravalueException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getStatus() {
        return errorCode.getHttpStatus().value();
    }
}
