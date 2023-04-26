package com.deploy.Travalue.travel.infrastructure;

import com.deploy.Travalue.travel.domain.Travel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TravelRepository extends JpaRepository<Travel, Long>{
    List<Travel> findTravelBySection(String section);
}
