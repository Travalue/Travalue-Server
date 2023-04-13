package com.deploy.Travalue.user.dto;

import com.deploy.Travalue.external.client.kakao.response.KaKaoProfileResponse;
import com.deploy.Travalue.user.domain.UserSocialType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CreateUserDto {
    @NotBlank(message = "{user.socialId.notBlank}")
    private String socialId;

    @NotNull(message = "{user.socialType.notNull}")
    private UserSocialType socialType;

    @NotBlank(message = "{user.nickname.notBlank}")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9-_]{2,15}$", message = "{user.nickname.format}")
    private String nickname;

    @NotNull(message = "{user.email.notNull")
    @Email(message = "{user.email.format}")
    private String email;

    private String profileImage;

    @Builder
    public CreateUserDto(UserSocialType socialType, KaKaoProfileResponse kaKaoProfileResponse) {
        this.socialId = kaKaoProfileResponse.getId();
        this.nickname = kaKaoProfileResponse.getProperties().getNickname();
        this.email = kaKaoProfileResponse.getKakaoAccount().getEmail();
        this.profileImage = kaKaoProfileResponse.getProperties().getProfileImage();
        this.socialType = socialType;
    }
}
