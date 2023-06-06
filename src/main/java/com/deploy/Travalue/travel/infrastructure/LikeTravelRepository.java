package com.deploy.Travalue.travel.infrastructure;

import com.deploy.Travalue.travel.domain.LikeTravel;
import com.deploy.Travalue.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeTravelRepository extends JpaRepository<LikeTravel, Long> {

    Optional<LikeTravel> findByUserIdAndTravelId(Long userId, Long travelId);
    int countLikeTravelByUser(User user);

    int countLikeTravelByTravelId(Long travelId);

    boolean existsByTravelIdAndUserId(Long travelId, Long userId);
}