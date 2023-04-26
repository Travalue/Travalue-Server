package com.deploy.Travalue.common.dto;

import com.deploy.Travalue.exception.ErrorCode;
import com.deploy.Travalue.exception.SuccessCode;
import lombok.*;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {
    private final int code;
    private final String message;
    private T data;

    public static ApiResponse success(SuccessCode successCode) {
        return new ApiResponse<>(successCode.getHttpStatus().value(), successCode.getMessage());
    }

    public static <T> ApiResponse<T> success(SuccessCode successStatus, T data) {
        return new ApiResponse<T>(successStatus.getHttpStatus().value(), successStatus.getMessage(), data);
    }

    public static ApiResponse error(ErrorCode errorCode) {
        return new ApiResponse<>(errorCode.getHttpStatus().value(), errorCode.getMessage());
    }
    public static ApiResponse error(ErrorCode errorCode, String message) {
        return new ApiResponse<>(errorCode.getHttpStatus().value(), message);
    }

}
