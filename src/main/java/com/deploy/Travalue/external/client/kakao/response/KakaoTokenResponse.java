package com.deploy.Travalue.external.client.kakao.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
@Getter
@ToString
@Slf4j
@NoArgsConstructor
public class KakaoTokenResponse {
    private String access_token;
    private String refresh_token;
}
