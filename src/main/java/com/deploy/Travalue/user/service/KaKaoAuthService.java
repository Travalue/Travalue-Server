package com.deploy.Travalue.user.service;

import com.deploy.Travalue.external.client.kakao.KaKaoApiClient;
import com.deploy.Travalue.external.client.kakao.KaKaoAuthClient;
import com.deploy.Travalue.external.client.kakao.response.KaKaoProfileResponse;
import com.deploy.Travalue.external.client.kakao.response.KakaoTokenResponse;
import com.deploy.Travalue.user.domain.SocialInformation;
import com.deploy.Travalue.user.domain.User;
import com.deploy.Travalue.user.domain.UserSocialType;
import com.deploy.Travalue.user.dto.CreateUserDto;
import com.deploy.Travalue.user.dto.request.LoginRequest;
import com.deploy.Travalue.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KaKaoAuthService implements AuthService{

    private static final UserSocialType socialType = UserSocialType.KAKAO;
    private final UserService userService;
    private final UserRepository userRepository;
    private final KaKaoApiClient kaKaoApiClient;
    private final KaKaoAuthClient kakaoAuthCaller;

    @Value("${kakao.client-id}")
    private String clientId;

    @Override
    public Long login(LoginRequest loginRequest) {
        // 1. 인가 코드를 가지고 Access_Token 받아오기
        KakaoTokenResponse kakaoTokenResponse = kakaoAuthCaller.getAccessToken(clientId, loginRequest.getCode(), "authorization_code");
        log.info("kaKaoProfileResponse : "+kakaoTokenResponse);

        // 2. Access_Token을 가지고 사용자 정보 가져오기
        KaKaoProfileResponse kaKaoProfileResponse = kaKaoApiClient.getProfileInfo("Bearer "+kakaoTokenResponse.getAccess_token());
        log.info("kaKaoProfileResponse : "+kaKaoProfileResponse);

        SocialInformation socialInformation = SocialInformation.builder()
            .socialId(kaKaoProfileResponse.getId())
            .socialType(socialType)
            .build();

        User user = userRepository.findBySocialInformation(socialInformation).orElse(null);

        if(user == null){
            // DB에 존재하지 않는 유저 -> 회원 가입 후 로그인
            log.info("가입 된 적 없는 회원...");
            CreateUserDto createUserDto = CreateUserDto.builder()
                .kaKaoProfileResponse(kaKaoProfileResponse)
                .socialType(loginRequest.getSocialType())
                .build();
            user = userService.registerUser(createUserDto);
        }else{
            log.info("이미 가입된 회원...");
        }
        return user.getId();
    }
}
