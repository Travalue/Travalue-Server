package com.deploy.Travalue.travel.service;

import com.deploy.Travalue.exception.model.NotFoundException;
import com.deploy.Travalue.travel.domain.*;
import com.deploy.Travalue.travel.infrastructure.*;
import com.deploy.Travalue.travel.service.dto.response.*;
import com.deploy.Travalue.user.controller.dto.SharedTravelDetailDto;
import com.deploy.Travalue.user.domain.User;
import com.deploy.Travalue.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TravelService {

    private final UserRepository userRepository;
    private final TravelRepository travelRepository;
    private final CategoryRepository categoryRepository;
    private final TravelPinRepository travelPinRepository;
    private final TravelContentRepository travelContentRepository;
    private final LikeTravelRepository likeTravelRepository;

    @Transactional
    public List<TrailersResponseDto> getTrailers() {
        final List<Travel> travel = travelRepository.findTravelBySection("trailer");
        return travel.stream()
                .map(trailer -> TrailersResponseDto.of(
                        trailer.getId(),
                        trailer.getSubject(),
                        trailer.getTitle(),
                        trailer.getSubTitle(),
                        trailer.getThumbnail()
                )).collect(Collectors.toList());
    }

    public List<TravellersResponseDto> getTravellers() {
        final List<Travel> travel = travelRepository.findTravelBySection("traveller");
        return travel.stream()
                .map(traveller -> TravellersResponseDto.of(
                        traveller.getId(),
                        traveller.getThumbnail()
                )).collect(Collectors.toList());
    }

    public TravelResponseDto getTravellerById(Long travelId, Long userId) {
        final Travel travel = travelRepository.findById(travelId).orElseThrow(() -> new NotFoundException("존재하지 않는 게시물입니다."));
        if (travel.isDeleted()) {
            throw new NotFoundException("삭제된 게시물입니다.");
        }
        if (!travel.isPublic()) {
            throw new NotFoundException("비공개 게시물입니다.");
        }
        int viewCount = travel.getViewCount();
        travel.setViewCount(++viewCount);
        travelRepository.save(travel);

        final User user = travel.getUser();
        final List<TravelPin> travelPins = travelPinRepository.findTravelPinByTravelIdOrderByOrderAsc(travelId);
        final List<TravelContent> travelContents = travelContentRepository.findTravelContentByTravelId(travelId);

        final List<TravelInformation> travelInformation = new ArrayList<>();
        for (TravelPin pin : travelPins) {
            travelInformation.add(pin.getTravelInformation());
        }

        List<TravelScheduleDto> schedules = travelInformation.stream().map(travelInfo -> TravelScheduleDto.of(
                travelInfo.getName(),
                travelInfo.getAddress(),
                travelInfo.getLatitude(),
                travelInfo.getLongitude()
        )).collect(Collectors.toList());

        List<TravelContentsDto> contents = travelContents.stream().map(travelContent -> TravelContentsDto.of(
                travelContent.getImageUrl(),
                travelContent.getContent()
        )).collect(Collectors.toList());

        String date = travel.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        TravelWriterDto writer = TravelWriterDto.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .description(user.getDescription())
                .profileImageURL(user.getProfileImage())
                .build();

        TravelStatisticsDto statistics = TravelStatisticsDto.builder()
                .isLiked(likeTravelRepository.existsByTravelIdAndUserId(travelId, userId))
                .likeCount(likeTravelRepository.countLikeTravelByTravelId(travelId))
                .viewCount(travel.getViewCount())
                .build();

        TravelResponseDto data = TravelResponseDto.builder()
                .title(travel.getTitle())
                .subTitle(travel.getSubTitle())
                .subject(travel.getSubject())
                .thumbnail(travel.getThumbnail())
                .date(date)
                .scheudles(schedules)
                .contents(contents)
                .writer(writer)
                .statistics(statistics)
                .build();

        return data;
    }

    public void deleteTravelById(Long travelId) {
        Travel travel = travelRepository.findById(travelId).orElseThrow(() -> new NotFoundException("존재하지 않는 게시물입니다."));
        travel.setDeleted(true);
        travelRepository.save(travel);
    }

    public List<SharedTravelDetailDto> getTravellersByProfileOwnerId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        List<Travel> travelList = travelRepository.findTravelByUser(user);

        // List<Travel> -> List<SharedTravelDetailDto>
        List<SharedTravelDetailDto> sharedTravelDetailDtoList = travelList.stream()
                .map(travel -> SharedTravelDetailDto.of(travel))
                .collect(Collectors.toList());

        return sharedTravelDetailDtoList;
    }

    public List<SharedTravelDetailDto> getTravellersByCategory(Long userId, Long categoryId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("존재하지 카테고리 id 입니다."));

        List<Travel> travelList = travelRepository.findTravelByUserAndCategory(user, category);

        // List<Travel> -> List<SharedTravelDetailDto>
        List<SharedTravelDetailDto> sharedTravelDetailDtoList = travelList.stream()
                .map(travel -> SharedTravelDetailDto.of(travel))
                .collect(Collectors.toList());

        return sharedTravelDetailDtoList;
    }

    public List<TravellersResponseDto> getSearchedTravellers(String keyword) {
        List<Travel> travelList = travelRepository.findTravelByIsPublicTrueAndIsDeletedFalseAndSectionAndTitleContainingOrSubTitleContaining("traveller", keyword, keyword);
        List<TravellersResponseDto> searchedTravellerList = travelList.stream().map(travel -> TravellersResponseDto.of(
                travel.getId(),
                travel.getThumbnail()
        )).collect(Collectors.toList());

        return searchedTravellerList;
    }

    public List<HotTravellersResponseDto> getHotTravellers() {
        List<Travel> travelList = travelRepository.findTop3ByIsPublicTrueAndIsDeletedFalseAndSectionOrderByCreatedAtDesc("traveller");
        List<HotTravellersResponseDto> hotTravellerList = travelList.stream().map(travel -> HotTravellersResponseDto.of(
                travel.getUser().getProfileImage(),
                travel.getUser().getNickname(),
                travel.getUser().getDescription(),
                travel.getId(),
                travel.getThumbnail(),
                travel.getSubject(),
                travel.getTitle(),
                travel.getSubTitle()
        )).collect(Collectors.toList());

        return hotTravellerList;
    }
}
