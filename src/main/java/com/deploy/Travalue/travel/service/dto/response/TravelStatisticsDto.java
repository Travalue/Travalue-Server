package com.deploy.Travalue.travel.service.dto.response;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TravelStatisticsDto {
    boolean isLiked;
    int likeCount;
    int viewCount;

    int postCount;

    @Builder
    public TravelStatisticsDto(boolean isLiked, int likeCount, int viewCount, int postCount) {
        this.isLiked = isLiked;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.postCount = postCount;
    }
}
