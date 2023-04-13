package com.deploy.Travalue.travel.service.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TrailersResponseDto {
    private Long trailerId;

    private String subject;

    private String title;

    private String subTitle;

    private String thumbnail;

    public static TrailersResponseDto of (Long trailerId, String subject, String title, String subTitle, String thumbnail){
        TrailersResponseDto response = new TrailersResponseDto(
                trailerId,
                subject,
                title,
                subTitle,
                thumbnail
        );
        return response;
    }

}
