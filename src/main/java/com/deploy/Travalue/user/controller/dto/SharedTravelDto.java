package com.deploy.Travalue.user.controller.dto;

import lombok.Getter;

@Getter
public class SharedTravelDto {
    private Long categoryId;
    private String title;
    private String subject;
    private String thumbnail;
    private Long count;

    public SharedTravelDto(Long categoryId, String title, String subject, String thumbnail, Long count) {
        this.categoryId = categoryId;
        this.title = title;
        this.subject = subject;
        this.thumbnail = thumbnail;
        this.count = count;
    }
}