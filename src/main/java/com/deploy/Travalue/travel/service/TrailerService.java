package com.deploy.Travalue.travel.service;

import com.deploy.Travalue.domain.travel.Travel;
import com.deploy.Travalue.travel.infrastructure.TravelRepository;
import com.deploy.Travalue.travel.service.dto.response.TrailersResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TrailerService {

    private final TravelRepository travelRepository;

    @Transactional(readOnly=true)
    public List<TrailersResponseDto> getTrailers() {
        final List<Travel> travel = travelRepository.findAll();
        return travel.stream()
                .map(trailer -> TrailersResponseDto.of(
                        trailer.getId(),
                        trailer.getSubject(),
                        trailer.getTitle(),
                        trailer.getSubTitle(),
                        trailer.getThumbnail()
                )).collect(Collectors.toList());
    }
}
