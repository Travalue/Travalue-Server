package com.deploy.Travalue.travel.service.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TravelContentsDto {
    private String imageURL;

    private String content;

    public static TravelContentsDto of (String imageURL, String content) {
        TravelContentsDto response = new TravelContentsDto(
                imageURL,
                content
        );
        return response;
    }
}
