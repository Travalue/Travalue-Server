package com.deploy.Travalue.user.controller.myTrip.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MyTripRequestDto {

    @NotBlank
    private String emoji;

    @NotBlank
    private String travelTitle;
}
