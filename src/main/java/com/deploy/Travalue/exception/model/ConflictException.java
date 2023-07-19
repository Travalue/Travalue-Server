package com.deploy.Travalue.exception.model;

import com.deploy.Travalue.exception.ErrorCode;

public class ConflictException extends TravalueException {
    public ConflictException(String message) {
        super(message, ErrorCode.CONFLICT_EXCEPTION);
    }
}
