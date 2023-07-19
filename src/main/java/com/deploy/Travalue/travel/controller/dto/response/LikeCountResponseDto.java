package com.deploy.Travalue.travel.controller.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LikeCountResponseDto {

    private Long likeCount;

    public static LikeCountResponseDto of(Long likeCount) {
        return new LikeCountResponseDto(likeCount);
    }
}
