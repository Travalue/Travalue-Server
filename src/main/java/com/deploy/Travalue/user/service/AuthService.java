package com.deploy.Travalue.user.service;

import com.deploy.Travalue.user.dto.request.LoginRequest;

public interface AuthService {
    Long login(LoginRequest loginRequest);
}
