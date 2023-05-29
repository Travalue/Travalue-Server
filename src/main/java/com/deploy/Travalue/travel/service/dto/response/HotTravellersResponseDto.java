package com.deploy.Travalue.travel.service.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HotTravellersResponseDto {
    private String profileImage;

    private String nickname;

    private String description;

    private Long travellerId;

    private String thumbnail;

    private String subject;

    private String title;

    private String subTitle;

    public static HotTravellersResponseDto of (String profileImage, String nickname, String description, Long travellerId, String thumbnail, String subject, String title, String subTitle) {
        HotTravellersResponseDto response = new HotTravellersResponseDto(
                profileImage,
                nickname,
                description,
                travellerId,
                thumbnail,
                subject,
                title,
                subTitle
        );
        return response;
    }
}
