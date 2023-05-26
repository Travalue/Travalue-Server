package com.deploy.Travalue.travel.infrastructure;

import com.deploy.Travalue.travel.domain.TravelPin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TravelPinRepository extends JpaRepository<TravelPin, Long> {
    List<TravelPin> findTravelPinByTravelIdOrderByOrderAsc(Long travelId);
}
