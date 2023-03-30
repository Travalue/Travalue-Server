package com.deploy.Travalue.user.controller.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddTripRequestDto {

    @NotBlank
    private String emoji;

    @NotBlank
    private String travelTitle;
}
