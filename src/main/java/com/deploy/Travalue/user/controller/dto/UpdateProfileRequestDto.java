package com.deploy.Travalue.user.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class UpdateProfileRequestDto {
    private String nickname;
    private String description;
    MultipartFile profileImage;
}
