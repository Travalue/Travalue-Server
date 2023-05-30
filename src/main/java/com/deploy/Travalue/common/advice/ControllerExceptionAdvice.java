package com.deploy.Travalue.common.advice;

import com.deploy.Travalue.common.dto.ApiResponse;
import com.deploy.Travalue.exception.ErrorCode;
import com.deploy.Travalue.exception.model.TravalueException;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
        FieldError fieldError = Objects.requireNonNull(e.getFieldError());
        return ApiResponse.error(ErrorCode.VALIDATION_EXCEPTION, String.format("%s. (%s)", fieldError.getDefaultMessage(), fieldError.getField()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FileSizeLimitExceededException.class)
    protected ApiResponse handleFileSizeLimitExceededException(final FileSizeLimitExceededException e) {
        return ApiResponse.error(ErrorCode.FILE_LIMIT_OVER_EXCEPTION);
    }

    /**
     * Travalue custom exception
     */
    @ExceptionHandler(TravalueException.class)
    protected ResponseEntity<ApiResponse> handleBaseException(TravalueException e) {
        return ResponseEntity.status(e.getStatus())
                .body(ApiResponse.error(e.getErrorCode(), e.getMessage()));
    }
}
