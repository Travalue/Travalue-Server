package com.deploy.Travalue.user.infrastructure.myTrip;

import com.deploy.Travalue.user.domain.myTrip.MyTrip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyTripRepository extends JpaRepository<MyTrip, Long> {
    List<MyTrip> findByUserId(Long userId);
}
