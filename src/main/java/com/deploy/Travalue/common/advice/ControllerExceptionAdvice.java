package com.deploy.Travalue.common.advice;

import com.deploy.Travalue.common.dto.ApiResponse;
import com.deploy.Travalue.exception.ErrorCode;
import com.deploy.Travalue.exception.model.TravalueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class ControllerExceptionAdvice {

    /**
     * 400 Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    protected ApiResponse handleBadRequest(final BindException e) {
        return ApiResponse.error(ErrorCode.VALIDATION_EXCEPTION, Objects.requireNonNull(e.getFieldError()).getDefaultMessage());
    }

    /**
     * Travalue custom exception
     */
    @ExceptionHandler(TravalueException.class)
    protected ResponseEntity<ApiResponse> handleBaseException(TravalueException exception) {
        return ResponseEntity.status(exception.getStatus())
                .body(ApiResponse.error(exception.getErrorCode()));
    }
}
