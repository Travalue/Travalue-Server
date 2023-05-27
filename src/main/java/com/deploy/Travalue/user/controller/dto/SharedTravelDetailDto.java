package com.deploy.Travalue.user.controller.dto;

import com.deploy.Travalue.travel.domain.Travel;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SharedTravelDetailDto {
    private final Long travelId;
    private final String title;
    private final String subTitle;
    private final String subject;
    private final String thumbnail;

    public SharedTravelDetailDto(Long travelId, String title, String subTitle, String subject, String thumbnail) {
        this.travelId = travelId;
        this.title = title;
        this.subTitle = subTitle;
        this.subject = subject;
        this.thumbnail = thumbnail;
    }

    public static SharedTravelDetailDto of(Travel travel) {
        return new SharedTravelDetailDto(travel.getId(), travel.getTitle(), travel.getSubTitle(), travel.getSubject(), travel.getThumbnail());
    }
}