package com.deploy.Travalue.trailer.infrastructure;

import com.deploy.Travalue.domain.travel.Travel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelRepository extends JpaRepository<Travel, Long> {
}
