package com.deploy.Travalue.external.client.kakao;

import com.deploy.Travalue.external.client.kakao.response.KakaoTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakaoAuthClient", url = "https://kauth.kakao.com")
public interface KaKaoAuthClient {

    @PostMapping("/oauth/token")
    KakaoTokenResponse getAccessToken(@RequestParam("client_id") String restApiKey,
        @RequestParam("redirect_uri") String redirectUrl,
        @RequestParam("code") String code,
        @RequestParam("grant_type") String grantType);
}
