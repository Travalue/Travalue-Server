package com.deploy.Travalue.user.dto;

import com.deploy.Travalue.user.domain.UserSocialType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.deploy.Travalue.user.dto.request.LoginRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CreateUserDto {
    @NotBlank(message = "{user.socialId.notBlank}")
    private String uniqueId;

    @NotNull(message = "{user.socialType.notNull}")
    private UserSocialType socialType;

    @NotNull(message = "{user.email.notNull")
    @Email(message = "{user.email.format}")
    private String email;

    private String profileImage;

    @Builder
    public CreateUserDto(LoginRequest loginRequest) {
        this.uniqueId = loginRequest.getUniqueId();
        this.email = loginRequest.getEmail();
        this.profileImage = loginRequest.getProfileImage();
        this.socialType = loginRequest.getSocialType();
    }
}
