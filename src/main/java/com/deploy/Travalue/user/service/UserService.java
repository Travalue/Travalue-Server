package com.deploy.Travalue.user.service;

import com.deploy.Travalue.common.dto.ApiResponse;
import com.deploy.Travalue.exception.SuccessCode;
import com.deploy.Travalue.exception.model.NotFoundException;
import com.deploy.Travalue.external.client.aws.S3Service;
import com.deploy.Travalue.travel.domain.TravelContentInfoVO;
import com.deploy.Travalue.travel.infrastructure.LikeTravelRepository;
import com.deploy.Travalue.travel.infrastructure.TravelRepository;
import com.deploy.Travalue.user.controller.dto.*;
import com.deploy.Travalue.user.domain.User;
import com.deploy.Travalue.user.domain.block.BlockUser;
import com.deploy.Travalue.user.domain.myTrip.MyTrip;
import com.deploy.Travalue.user.dto.CreateUserDto;
import com.deploy.Travalue.user.infrastructure.block.BlockUserRepository;
import com.deploy.Travalue.user.infrastructure.UserRepository;
import com.deploy.Travalue.user.infrastructure.myTrip.MyTripRepository;
import com.deploy.Travalue.user.service.myTrip.dto.response.MyTripResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final S3Service s3Service;
    private final UserRepository userRepository;
    private final MyTripRepository myTripRepository;
    private final TravelRepository travelRepository;
    private final BlockUserRepository blockUserRepository;
    private final LikeTravelRepository likeTravelRepository;

    public void updateNickname(Long userId, String nickname) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("DB에 존재하지 않는 USER ID입니다"));

        user.updateNickname(nickname);
    }

    public NicknameResponseDto checkNickname(String nickname) {
        User user = userRepository.findByNickname(nickname).orElse(null);
        boolean isDuplicate = false;
        if (user != null) { // 중복인 경우
            isDuplicate = true;
        }
        return new NicknameResponseDto(isDuplicate);
    }

    @Transactional
    User registerUser(CreateUserDto createUserDto) {
        User user = User.builder()
                .createUserDto(createUserDto)
                .build();

        log.info("회원가입 성공!!");
        return userRepository.save(user);
    }

    public MyPageResponseDto getMyPage(Long userId, Long pageOwnerUserId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));
        User pageOwner = userRepository.findById(pageOwnerUserId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        // 마이페이지를 조회하는 사람이 본인 여부 체크
        Boolean isMe = false;
        if (user == pageOwner)
            isMe = true;

        // 나의 여행지 리스트
        final List<MyTrip> myTripList = myTripRepository.findByUserId(pageOwnerUserId);
        // List<MyTrip> -> List<MyTripResponseDto>
        List<MyTripResponseDto> travelList = myTripList.stream()
                .map(trip -> MyTripResponseDto.of(trip.getEmoji(), trip.getTravelTitle()))
                .collect(Collectors.toList());

        // 공유중인 여행지 리스트
        List<SharedTravelDto> sharedTravelDtoList;
        if(isMe){
            // 본인인 경우 비공개 게시물 까지 조회
            sharedTravelDtoList = travelRepository.findMySharedTravelList(pageOwner);
        }else{
            sharedTravelDtoList = travelRepository.findSharedTravelList(pageOwner);
        }

        // 공유중인 여행지 개수
        int sharedTravelCount = 0;
        for (SharedTravelDto sharedTravelDto : sharedTravelDtoList) {
            sharedTravelCount += sharedTravelDto.getCount();
        }

        // 트레블러/트레일러 좋아용 목록
        int travelLikeCount = likeTravelRepository.countLikeTravelByUser(pageOwner);

        MyPageResponseDto myPageResponseDto = MyPageResponseDto.builder()
                .isMe(isMe)
                .user(pageOwner)
                .travelList(travelList)
                .sharedTravelCount(sharedTravelCount)
                .sharedTravel(sharedTravelDtoList)
                .travelLikeCount(travelLikeCount)
                .build();

        return myPageResponseDto;
    }

    @Transactional
    public void blockUser(Long myId, Long userId) {
        User user = userRepository.findById(myId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        User blockedUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("차단 할 유저가 존재 하지않습니다."));

        Optional<BlockUser> alreadyBlockedUser = blockUserRepository.findBlockUsersByBlockUserAndBlockedUser(user, blockedUser);

        if (alreadyBlockedUser.isPresent()) {
            throw new IllegalArgumentException("이미 차단한 유저입니다.");
        }

        BlockUser blockUser = BlockUser.builder()
                .blockUser(user)
                .blockedUser(blockedUser)
                .build();

        blockUserRepository.save(blockUser);
    }

    @Transactional
    public void unblockUser(Long myId, Long userId) {
        User user = userRepository.findById(myId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        User blockedUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("차단 해제할 유저가 존재 하지않습니다."));
        BlockUser blockUser = blockUserRepository.findBlockUsersByBlockUserAndBlockedUser(user, blockedUser)
                .orElseThrow(() -> new IllegalArgumentException("차단되지 않은 유저입니다."));
        blockUserRepository.delete(blockUser);
    }

    @Transactional
    public void updateProfile(UpdateProfileRequestDto updateProfileRequestDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        if(updateProfileRequestDto.getProfileImage() != null){
            String profileImagePath = s3Service.uploadImage(updateProfileRequestDto.getProfileImage(), "profileImage");
            user.updateAllProfile(updateProfileRequestDto, profileImagePath);
        }else{
            user.updateProfile(updateProfileRequestDto);
        }
    }
}