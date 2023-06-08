package com.deploy.Travalue.user.service;

import com.deploy.Travalue.user.dto.request.LoginRequest;
import com.deploy.Travalue.user.dto.response.LoginServiceResponseDto;

public interface AuthService {
    LoginServiceResponseDto login(LoginRequest loginRequest);
}
