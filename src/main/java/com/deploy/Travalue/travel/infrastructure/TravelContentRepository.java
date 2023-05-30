package com.deploy.Travalue.travel.infrastructure;

import com.deploy.Travalue.travel.domain.TravelContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TravelContentRepository extends JpaRepository<TravelContent, Long> {
    List<TravelContent> findTravelContentByTravelId(Long travelId);
    void deleteByTravelId(Long travelId);
    List<TravelContent> findTravelContentByTravelIdOrderByPinIndexAsc(Long travelId);
}
