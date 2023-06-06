package com.deploy.Travalue.travel.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class TravelContentInfoVO {

    @NotNull
    private MultipartFile image;

    @NotNull
    private String content;
}
