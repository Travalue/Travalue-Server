package com.deploy.Travalue.travel.service;

import com.deploy.Travalue.exception.model.NotFoundException;
import com.deploy.Travalue.travel.controller.dto.request.TravellerRequestDto;
import com.deploy.Travalue.travel.domain.*;
import com.deploy.Travalue.travel.infrastructure.*;
import com.deploy.Travalue.travel.service.dto.response.*;
import com.deploy.Travalue.user.domain.User;
import com.deploy.Travalue.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TravelService {

    private final UserRepository userRepository;
    private final TravelRepository travelRepository;
    private final TravelPinRepository travelPinRepository;
    private final TravelContentRepository travelContentRepository;
    private final CategoryRepository categoryRepository;
    private final TravelInformationRepository travelInformationRepository;

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

    public TravelResponseDto getTravellerById(Long travelId) {
        final Travel travel = travelRepository.findById(travelId).orElseThrow(() -> new NotFoundException("존재하지 않는 게시물입니다."));
        final User user = travel.getUser();
        final List<TravelPin> travelPins = travelPinRepository.findTravelPinByTravelIdOrderByPinIndexAsc(travelId);
        final List<TravelContent> travelContents = travelContentRepository.findTravelContentByTravelId(travelId);

        final List<TravelInformation> travelInformation = new ArrayList<>();

        for(TravelPin pin: travelPins) {
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

        TravelWriterDto writer = new TravelWriterDto();
        writer.setUserId(user.getId());
        writer.setNickname(user.getNickname());
        writer.setDescription(user.getDescription());
        writer.setProfileImageURL(user.getProfileImage());

        TravelResponseDto data = new TravelResponseDto();
        data.setTitle(travel.getTitle());
        data.setSubTitle(travel.getSubTitle());
        data.setSubject(travel.getSubject());
        data.setThumbnail(travel.getThumbnail());
        data.setDate(date);
        data.setSchedules(schedules);
        data.setContents(contents);
        data.setWriter(writer);

        // viewCount++
        travel.increaseViewCount();
        travelRepository.save(travel);

        return data;
    }
}
