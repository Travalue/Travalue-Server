package com.deploy.Travalue.trailer.service;

import com.deploy.Travalue.domain.travel.Travel;
import com.deploy.Travalue.trailer.infrastructure.TravelRepository;
import com.deploy.Travalue.trailer.service.dto.response.TrailersResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TrailerService {

    private final TravelRepository travelRepository;

    @Transactional
    public List<TrailersResponseDto> getTrailers() {
        final Travel travel = (Travel) travelRepository.findAll();
        return (List<TrailersResponseDto>) travel;
    }
}
