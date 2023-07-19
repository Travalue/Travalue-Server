package com.deploy.Travalue.travel.service.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryListResponseDto {

    private String title;
    private Long travellerCount;

    public static CategoryListResponseDto of(String title, Long travellerCount) {
        return new CategoryListResponseDto(title, travellerCount);
    }
}
