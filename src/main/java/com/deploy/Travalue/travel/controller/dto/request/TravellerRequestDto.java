package com.deploy.Travalue.travel.controller.dto.request;

import com.deploy.Travalue.travel.domain.TravelContentInfoVO;
import com.deploy.Travalue.travel.domain.TravelRoutineInfoVO;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class TravellerRequestDto {

    @NotNull
    private Long categoryId;
    @NotNull
    private MultipartFile thumbnail;

    @NotBlank
    private String title;

    @NotBlank
    private String subTitle;

    @NotBlank
    private String subject;

    @NotNull
    private Boolean isPublic;

    private String region;

    @NotNull
    private List<TravelRoutineInfoVO> travelRoutineInfoList;

    @NotNull
    private List<TravelContentInfoVO> travelContentInfoList;
}
