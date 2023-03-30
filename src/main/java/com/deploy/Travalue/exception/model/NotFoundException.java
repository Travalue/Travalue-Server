package com.deploy.Travalue.exception.model;

import com.deploy.Travalue.exception.ErrorCode;

public class NotFoundException extends TravalueException {
    public NotFoundException(String message) {
        super(message, ErrorCode.NOT_FOUND_EXCEPTION);
    }
}
