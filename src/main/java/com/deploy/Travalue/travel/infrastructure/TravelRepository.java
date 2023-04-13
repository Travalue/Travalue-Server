package com.deploy.Travalue.travel.infrastructure;

import com.deploy.Travalue.domain.travel.Travel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TravelRepository extends JpaRepository<Travel, Long> {
    List<Travel> findTravelBySection(String section);
}
