package com.deploy.Travalue.travel.service;

import com.deploy.Travalue.exception.model.ConflictException;
import com.deploy.Travalue.exception.model.NotFoundException;
import com.deploy.Travalue.exception.model.UnauthorizedException;
import com.deploy.Travalue.external.client.aws.S3Service;
import com.deploy.Travalue.travel.controller.dto.request.TravellerRequestDto;
import com.deploy.Travalue.travel.controller.dto.response.LikeCountResponseDto;
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
    private final TravelInformationRepository travelInformationRepository;
    private final LikeTravelRepository likeTravelRepository;
    private final S3Service s3Service;

    @Transactional
    public void create(Long userId, String thumbnailImageUrl, List<String> travellerContentImageUrlList, TravellerRequestDto request) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        final Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 카테고리입니다."));

        for (TravelRoutineInfoVO travelInfo : request.getTravelRoutineInfoList()) {
            if (travelInformationRepository.existsByAddress(travelInfo.getAddress())) {
                continue;
            } else {
                travelInformationRepository.save(TravelInformation.newInstance(
                        travelInfo.getPlaceName(),
                        travelInfo.getAddress(),
                        travelInfo.getLatitude(),
                        travelInfo.getLongitude()
                ));
            }
        }

        final Travel newTravel = Travel.newInstance(
                category,
                user,
                thumbnailImageUrl,
                request.getSubject(),
                request.getTitle(),
                request.getSubTitle(),
                request.getRegion(),
                "traveller",
                request.getIsPublic()
        );

        travelRepository.save(newTravel);

        for (int i = 0; i < request.getTravelRoutineInfoList().size(); i++) {
            final TravelInformation travelInformation = travelInformationRepository.findByAddress(request.getTravelRoutineInfoList().get(i).getAddress())
                    .orElseThrow(() -> new NotFoundException("존재하지 않는 여행지 정보입니다."));

            travelPinRepository.save(TravelPin.newInstance(
                    newTravel,
                    travelInformation,
                    i
            ));
        }

        for (int i = 0; i < request.getTravelContentInfoList().size(); i++) {

            TravelContentInfoVO travelContent = request.getTravelContentInfoList().get(i);

            travelContentRepository.save(TravelContent.newInstance(
                    newTravel,
                    travelContent.getContent(),
                    travellerContentImageUrlList.get(i),
                    i
            ));
        }
    }

    @Transactional
    public void update(Long userId, Long travellerId, String thumbnailImageUrl, List<String> travellerContentImageUrlList, TravellerRequestDto request) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        final Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 카테고리입니다."));

        final Travel traveller = travelRepository.findById(travellerId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시글입니다."));

        if (!traveller.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("해당 게시글에 권한이 없습니다.");
        }

        travelPinRepository.deleteByTravelId(travellerId);
        travelContentRepository.deleteByTravelId(travellerId);

        for (TravelRoutineInfoVO travelInfo : request.getTravelRoutineInfoList()) {
            if (travelInformationRepository.existsByAddress(travelInfo.getAddress())) {
                continue;
            } else {
                travelInformationRepository.save(TravelInformation.newInstance(
                        travelInfo.getPlaceName(),
                        travelInfo.getAddress(),
                        travelInfo.getLatitude(),
                        travelInfo.getLongitude()
                ));
            }
        }

        for (int i = 0; i < request.getTravelRoutineInfoList().size(); i++) {
            final TravelInformation travelInformation = travelInformationRepository.findByAddress(request.getTravelRoutineInfoList().get(i).getAddress())
                    .orElseThrow(() -> new NotFoundException("존재하지 않는 여행지 정보입니다."));

            travelPinRepository.save(TravelPin.newInstance(
                    traveller,
                    travelInformation,
                    i
            ));
        }

        for (int i = 0; i < request.getTravelContentInfoList().size(); i++) {

            TravelContentInfoVO travelContent = request.getTravelContentInfoList().get(i);

            travelContentRepository.save(TravelContent.newInstance(
                    traveller,
                    travelContent.getContent(),
                    travellerContentImageUrlList.get(i),
                    i
            ));
        }

        s3Service.deleteFile(traveller.getThumbnail());
        List<TravelContent> travelContentList = travelContentRepository.findTravelContentByTravelId(travellerId);
        for (TravelContent travelContent : travelContentList) {
            s3Service.deleteFile(travelContent.getImageUrl());
        }


        traveller.update(
                category,
                thumbnailImageUrl,
                request.getSubject(),
                request.getTitle(),
                request.getSubTitle(),
                request.getRegion(),
                "traveller",
                request.getIsPublic()
        );
    }

    @Transactional
    public List<TrailersResponseDto> getTrailers() {
        final List<Travel> travel = travelRepository.findTravelByIsPublicTrueAndIsDeletedFalseAndSection("trailer");
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
        final List<Travel> travel = travelRepository.findTravelByIsPublicTrueAndIsDeletedFalseAndSection("traveller");
        return travel.stream()
                .map(traveller -> TravellersResponseDto.of(
                        traveller.getId(),
                        traveller.getThumbnail()
                )).collect(Collectors.toList());
    }

    public TravelResponseDto getTravelById(Long travelId, Long userId) {
        final Travel travel = travelRepository.findById(travelId).orElseThrow(() -> new NotFoundException("존재하지 않는 게시물입니다."));
        if (travel.isDeleted()) {
            throw new NotFoundException("삭제된 게시물입니다.");
        }
        if (!travel.getIsPublic()) {
            throw new NotFoundException("비공개 게시물입니다.");
        }
        int viewCount = travel.getViewCount();
        travel.setViewCount(++viewCount);
        travelRepository.save(travel);

        final User user = travel.getUser();
        final List<TravelPin> travelPins = travelPinRepository.findTravelPinByTravelIdOrderByPinIndexAsc(travelId);
        final List<TravelContent> travelContents = travelContentRepository.findTravelContentByTravelIdOrderByPinIndexAsc(travelId);

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

        List<Travel> travelList = travelRepository.findTravelByUser(user);

        TravelWriterDto writer = TravelWriterDto.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .description(user.getDescription())
                .profileImageURL(user.getProfileImage())
                .postCount(travelList.size())
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
        if (travel.isDeleted()) {
            throw new NotFoundException("이미 삭제된 게시물입니다.");
        }
        travel.setDeleted(true);
        travelRepository.save(travel);
    }

    public List<SharedTravelDetailDto> getTravellersByProfileOwnerId(Long userId, Long pageOwnerUserId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));
        User pageOwner = userRepository.findById(pageOwnerUserId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        List<Travel> travelList;
        if (user == pageOwner) {
            // 본인인 경우 비공개 게시물도 보여줌
            travelList = travelRepository.findTravelByUserAndIsDeletedFalse(pageOwner);
        } else {
            travelList = travelRepository.findTravelByUserAndIsDeletedFalseAndIsPublicTrue(pageOwner);
        }

        // List<Travel> -> List<SharedTravelDetailDto>
        List<SharedTravelDetailDto> sharedTravelDetailDtoList = travelList.stream()
                .map(travel -> SharedTravelDetailDto.of(travel))
                .collect(Collectors.toList());

        return sharedTravelDetailDtoList;
    }

    public List<SharedTravelDetailDto> getTravellersByCategory(Long userId, Long pageOwnerUserId, Long categoryId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));
        User pageOwner = userRepository.findById(pageOwnerUserId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("존재하지 카테고리 id 입니다."));

        List<Travel> travelList;
        if (user == pageOwner) {
            // 본인인 경우 비공개 게시물도 보여줌
            travelList = travelRepository.findTravelByUserAndCategoryAndIsDeletedFalse(pageOwner, category);
        } else {
            travelList = travelRepository.findTravelByUserAndCategoryAndIsDeletedFalseAndIsPublicTrue(pageOwner, category);
        }

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

    @Transactional
    public LikeCountResponseDto travelLike(Long userId, Long postId) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        final Travel travel = travelRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시글입니다."));

        if (likeTravelRepository.existsByTravelIdAndUserId(travel.getId(), user.getId())) {
            throw new ConflictException("이미 좋아요를 누른 게시물입니다.");
        }

        likeTravelRepository.save(LikeTravel.newInstance(user, travel));
        Long likeCount = likeTravelRepository.countByTravelId(travel.getId());

        return LikeCountResponseDto.of(likeCount);
    }

    @Transactional
    public LikeCountResponseDto travelUnlike(Long userId, Long postId) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        final Travel travel = travelRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시글입니다."));

        LikeTravel likeTravel = likeTravelRepository.findByUserIdAndTravelId(user.getId(), travel.getId())
                .orElseThrow(() -> new NotFoundException("좋아요를 누른 적이 없는 게시글입니다."));

        likeTravelRepository.deleteById(likeTravel.getId());
        Long likeCount = likeTravelRepository.countByTravelId(travel.getId());

        return LikeCountResponseDto.of(likeCount);
    }
}
