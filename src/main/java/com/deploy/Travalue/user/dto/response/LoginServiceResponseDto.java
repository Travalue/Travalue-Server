package com.deploy.Travalue.user.dto.response;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginServiceResponseDto {

    private Long userId;
    private Boolean isSignup;

    @Builder
    public LoginServiceResponseDto(Long userId, Boolean isSignup) {
        this.userId = userId;
        this.isSignup = isSignup;
    }
}
