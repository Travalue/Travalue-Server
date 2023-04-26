package com.deploy.Travalue.travel.service;

import com.deploy.Travalue.travel.domain.Travel;
import com.deploy.Travalue.travel.infrastructure.TravelRepository;
import com.deploy.Travalue.travel.service.dto.response.TrailersResponseDto;
import com.deploy.Travalue.travel.service.dto.response.TravellersResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TravelService {

    private final TravelRepository travelRepository;

    @Transactional
    public void getTrailers() {
//        final List<Travel> travel = travelRepository.findTravelBySection("trailer");
//        return travel.stream()
//                .map(trailer -> TrailersResponseDto.of(
//                        trailer.getId(),
//                        trailer.getSubject(),
//                        trailer.getTitle(),
//                        trailer.getSubTitle(),
//                        trailer.getThumbnail()
//                )).collect(Collectors.toList());
    }

    @Transactional
    public List<TravellersResponseDto> getTravellers() {
        final List<Travel> travel = travelRepository.findTravelBySection("traveller");
        return travel.stream()
                .map(traveller -> TravellersResponseDto.of(
                        traveller.getId(),
                        traveller.getThumbnail()
                )).collect(Collectors.toList());
    }
}
