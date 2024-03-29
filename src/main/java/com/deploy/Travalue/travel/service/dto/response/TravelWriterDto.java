package com.deploy.Travalue.travel.service.dto.response;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TravelWriterDto {
    private Long userId;

    private String nickname;

    private String description;

    private String profileImageURL;

    private int postCount;

    @Builder
    public TravelWriterDto(Long userId, String nickname, String description, String profileImageURL, int postCount) {
        this.userId = userId;
        this.nickname = nickname;
        this.description = description;
        this.profileImageURL = profileImageURL;
        this.postCount = postCount;
    }
}
