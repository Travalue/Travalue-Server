package com.deploy.Travalue.user.controller;

import com.deploy.Travalue.common.dto.ApiResponse;
import com.deploy.Travalue.config.jwt.JwtService;
import com.deploy.Travalue.exception.SuccessCode;
import com.deploy.Travalue.user.dto.request.LoginRequest;
import com.deploy.Travalue.user.dto.response.LoginResponse;
import com.deploy.Travalue.user.service.AuthService;
import com.deploy.Travalue.user.service.AuthServiceProvider;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtService jwtService;
    private final AuthServiceProvider authServiceProvider;

    @ApiOperation("소셜 로그인 페이지")
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody final LoginRequest loginRequest) {
        final AuthService authService = authServiceProvider.getAuthService(loginRequest.getSocialType());
        final Long userId = authService.login(loginRequest);
        final String token = jwtService.issuedToken(String.valueOf(userId), "USER", 60 * 60 * 24 * 365L); // TODO:임의로 365일 (기존 30일)
        return ApiResponse.success(SuccessCode.LOGIN_SUCCESS, new LoginResponse(token, userId));
    }
}
