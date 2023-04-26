package com.deploy.Travalue.travel.controller.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryRequestDto {

    @NotBlank
    @Pattern(regexp = "^[가-힣a-zA-Z\\s]{1,10}$", message = "카테고리 제목 형식에 맞지 않습니다.")
    private String title;

    @NotBlank
    private String subject;

    private String region;

    @NotNull
    private MultipartFile thumbnail;
}
