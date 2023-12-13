package com.deploy.Travalue.travel.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import com.deploy.Travalue.travel.domain.ReportTravel;

public interface ReportTravelRepository extends JpaRepository<ReportTravel, Long> {
    boolean existsByReportedTravelIdAndUserId(Long travelId, Long userId);
}
