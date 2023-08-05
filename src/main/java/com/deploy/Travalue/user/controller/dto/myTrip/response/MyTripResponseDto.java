package com.deploy.Travalue.user.controller.dto.myTrip.response;

import lombok.*;

import javax.validation.constraints.NotBlank;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MyTripResponseDto {

    private Long id;
    private String emoji;
    private String travelTitle;

    public static MyTripResponseDto of(Long id, String emoji, String travelTitle) {
        return new MyTripResponseDto(id, emoji, travelTitle);
    }
}
