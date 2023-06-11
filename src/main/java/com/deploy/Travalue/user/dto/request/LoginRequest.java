package com.deploy.Travalue.user.dto.request;

import com.deploy.Travalue.user.domain.UserSocialType;

import javax.validation.constraints.Email;
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
    private String uniqueId;

    @NotNull(message = "{user.socialType.notNull}")
    private UserSocialType socialType;

    @NotNull(message = "{user.email.notNull")
    @Email(message = "{user.email.format}")
    private String email;

    private String profileImage;

    @Builder
    public LoginRequest(String uniqueId, UserSocialType socialType, String email, String profileImage) {
        this.uniqueId = uniqueId;
        this.socialType = socialType;
        this.email = email;
        this.profileImage = profileImage;
    }
}