package com.deploy.Travalue.user.controller;

import com.deploy.Travalue.common.dto.ApiResponse;
import com.deploy.Travalue.config.interceptor.Auth;
import com.deploy.Travalue.config.resolver.UserId;
import com.deploy.Travalue.exception.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {

    @Auth
    @GetMapping("/test")
    public ApiResponse<?> login(@UserId Long userId) {
        log.info("userId : " + userId);
        return ApiResponse.success(SuccessCode.LOGIN_SUCCESS, null);
    }
}
