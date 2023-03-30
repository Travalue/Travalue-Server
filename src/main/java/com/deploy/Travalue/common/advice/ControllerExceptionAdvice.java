package com.deploy.Travalue.common.advice;

import com.deploy.Travalue.common.dto.ApiResponse;
import com.deploy.Travalue.exception.model.TravalueException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionAdvice {
    /**
     * Travalue custom exception
     */
    @ExceptionHandler(TravalueException.class)
    protected ResponseEntity<ApiResponse> handleBaseException(TravalueException exception) {
        return ResponseEntity.status(exception.getStatus())
                .body(ApiResponse.error(exception.getErrorCode(), exception.getMessage()));
    }
}
