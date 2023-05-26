package com.deploy.Travalue.travel.service.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class TravelResponseDto {
    private String title;

    private String subTitle;

    private String subject;

    private String thumbnail;

    private String date;

    private List<TravelScheduleDto> schedules;

    private List<TravelContentsDto> contents;

    private TravelWriterDto writer;

    public static TravelResponseDto of (String title, String subTitle, String subject, String thumbnail, String date, List<TravelScheduleDto> schedules, List<TravelContentsDto> contents, TravelWriterDto writer) {
        TravelResponseDto response = new TravelResponseDto(
                title,
                subTitle,
                subject,
                thumbnail,
                date,
                schedules,
                contents,
                writer
        );
        return response;
    }
}
