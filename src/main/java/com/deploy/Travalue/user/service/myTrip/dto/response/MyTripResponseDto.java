package com.deploy.Travalue.user.service.myTrip.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MyTripResponseDto {

    private String emoji;

    private String travelTitle;

    private MyTripResponseDto(final String emoji, final  String travelTitle) {
        this.emoji = emoji;
        this.travelTitle = travelTitle;
    }

    public static MyTripResponseDto of(final String emoji, final String travelTitle) {
        return new MyTripResponseDto(emoji, travelTitle);
    }
}
