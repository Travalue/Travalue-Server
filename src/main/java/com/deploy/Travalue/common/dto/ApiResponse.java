package com.deploy.Travalue.common.dto;

import com.deploy.Travalue.common.exception.ErrorCode;
import com.deploy.Travalue.common.exception.StatusCode;
import com.deploy.Travalue.common.exception.SuccessCode;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {
    public static final ApiResponse<String> SUCCESS = success("OK");

    private StatusCode statusCode;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(SuccessCode successCode) {
        return new ApiResponse<>(StatusCode.SUCCESS, successCode.getMessage(), null);
    }

    public static <T> ApiResponse<T> success(SuccessCode successCode, T data) {
        return new ApiResponse<>(StatusCode.SUCCESS, successCode.getMessage(), data);
    }

    public static <T> ApiResponse<T> error(ErrorCode errorCode) {
        return new ApiResponse<>(errorCode.getStatusCode(), errorCode.getMessage(), null);
    }

    public static <T> ApiResponse<T> error(ErrorCode errorCode, String message) {
        return new ApiResponse<>(errorCode.getStatusCode(), message, null);
    }
}
