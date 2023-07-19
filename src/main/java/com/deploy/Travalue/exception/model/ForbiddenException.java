package com.deploy.Travalue.exception.model;

import com.deploy.Travalue.exception.ErrorCode;

public class ForbiddenException extends TravalueException {

    public ForbiddenException(String message) {
        super(message, ErrorCode.CONFLICT_EXCEPTION);
    }
}
