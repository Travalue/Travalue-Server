package com.deploy.Travalue.external.client.kakao;

import com.deploy.Travalue.external.client.kakao.response.KaKaoProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakaoAuthApiClient", url = "https://kapi.kakao.com")
public interface KaKaoApiClient {

    @GetMapping("/v2/user/me")
    KaKaoProfileResponse getProfileInfo(@RequestHeader("Authorization") String accessToken);
}