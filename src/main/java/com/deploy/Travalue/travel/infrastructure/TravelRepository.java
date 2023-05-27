package com.deploy.Travalue.travel.infrastructure;

import com.deploy.Travalue.travel.domain.Travel;
import com.deploy.Travalue.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TravelRepository extends JpaRepository<Travel, Long>, TravelCustomRepository {
    List<Travel> findTravelBySection(String section);

    List<Travel> findTravelByUser(User user);
}
