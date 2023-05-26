package com.deploy.Travalue.travel.service.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TravelScheduleDto {
    private String name;

    private String address;

    private Long latitude;

    private Long longitude;

    public static TravelScheduleDto of (String name, String address, Long latitude, Long longitude) {
        TravelScheduleDto response = new TravelScheduleDto(
                name,
                address,
                latitude,
                longitude
        );
        return response;
    }
}
