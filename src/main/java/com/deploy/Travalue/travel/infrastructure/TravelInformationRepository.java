package com.deploy.Travalue.travel.infrastructure;

import com.deploy.Travalue.travel.domain.TravelInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TravelInformationRepository extends JpaRepository<TravelInformation, Long> {
    boolean existsByAddress(String address);
    Optional<TravelInformation> findByAddress(String address);
}
