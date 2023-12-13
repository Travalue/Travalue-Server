package com.deploy.Travalue.travel.controller.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class ReportTravelRequestDto {
    @NotNull
    private String reason;

}
