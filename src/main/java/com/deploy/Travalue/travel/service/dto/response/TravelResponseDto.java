package com.deploy.Travalue.travel.service.dto.response;

import lombok.*;

import java.util.List;

@Getter
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

    private TravelStatisticsDto statistics;



    @Builder
    public TravelResponseDto(String title, String subTitle, String subject, String thumbnail, String date, List<TravelScheduleDto> scheudles, List<TravelContentsDto> contents, TravelWriterDto writer, TravelStatisticsDto statistics) {
        this.title = title;
        this.subTitle = subTitle;
        this.subject = subject;
        this.thumbnail = thumbnail;
        this.date = date;
        this.schedules = scheudles;
        this.contents = contents;
        this.writer = writer;
        this.statistics = statistics;
    }
}
