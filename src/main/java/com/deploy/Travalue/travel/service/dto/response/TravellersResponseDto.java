package com.deploy.Travalue.travel.service.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access =  AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TravellersResponseDto {
    private Long travellerId;

    private String thumbnail;

    public static TravellersResponseDto of (Long travellerId, String thumbnail) {
        TravellersResponseDto response = new TravellersResponseDto(
                travellerId,
                thumbnail
        );
        return response;
    }
}
