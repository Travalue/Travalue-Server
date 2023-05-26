package com.deploy.Travalue.travel.service.dto.response;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class TravelWriterDto {
    private Long userId;

    private String nickname;

    private String description;

    private String profileImageURL;
}
