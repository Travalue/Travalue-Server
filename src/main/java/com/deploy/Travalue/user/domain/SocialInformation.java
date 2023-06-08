package com.deploy.Travalue.user.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SocialInformation {
    @Column(length = 200, nullable = false)
    private String socialId;

    @Column(length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private UserSocialType socialType;

    @Builder
    public SocialInformation(String socialId, UserSocialType socialType) {
        this.socialId = socialId;
        this.socialType = socialType;
    }
}