package com.deploy.Travalue.user.controller.dto;

import com.deploy.Travalue.user.domain.User;
import com.deploy.Travalue.user.service.myTrip.dto.response.MyTripResponseDto;

import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MyPageResponseDto {

    String nickname;
    String profileImage;
    String description;
    int travelCount;
    List<MyTripResponseDto> travelList;
    int sharedTravelCount;
    List<SharedTravelDto> sharedTravel;
    int travelLikeCount;

    @Builder
    public MyPageResponseDto(User user, List<MyTripResponseDto> travelList, int sharedTravelCount, List<SharedTravelDto> sharedTravel, int travelLikeCount) {
        this.nickname = user.getNickname();
        this.profileImage = user.getProfileImage();
        this.description = user.getDescription();
        this.travelCount = user.getTravelCount();
        this.travelList = travelList;
        this.sharedTravelCount = sharedTravelCount;
        this.sharedTravel = sharedTravel;
        this.travelLikeCount = travelLikeCount;
    }
}
