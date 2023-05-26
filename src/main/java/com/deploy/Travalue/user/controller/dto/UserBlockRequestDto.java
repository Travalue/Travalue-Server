package com.deploy.Travalue.user.controller.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserBlockRequestDto {
    private Long blockUserUid;
    private boolean blocked;
}
