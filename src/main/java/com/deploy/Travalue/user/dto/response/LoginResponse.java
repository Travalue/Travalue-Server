package com.deploy.Travalue.user.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginResponse {
    private String token;
    private Long userId;

    @Builder
    public LoginResponse(String token, Long userId) {
        this.token = token;
        this.userId = userId;
    }
}
