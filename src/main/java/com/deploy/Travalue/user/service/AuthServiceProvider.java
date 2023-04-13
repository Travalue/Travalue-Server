package com.deploy.Travalue.user.service;

import com.deploy.Travalue.user.domain.UserSocialType;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthServiceProvider{
    private static final Map<UserSocialType, AuthService> authServiceMap = new HashMap<>();

//    private final AppleAuthService appleAuthService;
    private final KaKaoAuthService kaKaoAuthService;

    @PostConstruct
    void initializeAuthServicesMap() {
//        authServiceMap.put(UserSocialType.APPLE, appleAuthService);
        authServiceMap.put(UserSocialType.KAKAO, kaKaoAuthService);
    }

    public AuthService getAuthService(UserSocialType socialType) {
        return authServiceMap.get(socialType);
    }
}
