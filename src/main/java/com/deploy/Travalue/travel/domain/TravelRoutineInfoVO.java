package com.deploy.Travalue.travel.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class TravelRoutineInfoVO {

    @NotBlank
    private String placeName;

    @NotBlank
    private String address;

    @NotNull
    private Long latitude;

    @NotNull
    private Long longitude;
}
