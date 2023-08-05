package com.deploy.Travalue.user.controller.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MyPageUpdateRequestDto {

    private UpdateProfileRequestDto updateProfileRequestDto;
    private MultipartFile profileImage;
}
