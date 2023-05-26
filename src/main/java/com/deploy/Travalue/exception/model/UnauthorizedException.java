package com.deploy.Travalue.exception.model;

import com.deploy.Travalue.exception.ErrorCode;

public class UnauthorizedException extends TravalueException {
    public UnauthorizedException(String message) {
        super(message, ErrorCode.UNAUTHORIZED_EXCEPTION);
    }
}
