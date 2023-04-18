package com.deploy.Travalue.user.dto.request;

import com.deploy.Travalue.user.domain.UserSocialType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginRequest {

    @NotBlank(message = "{auth.token.notBlank}")
    private String code;

    @NotNull(message = "{user.socialType.notNull}")
    private UserSocialType socialType;

    @Builder
    public LoginRequest(String code, UserSocialType socialType) {
        this.code = code;
        this.socialType = socialType;
    }
}