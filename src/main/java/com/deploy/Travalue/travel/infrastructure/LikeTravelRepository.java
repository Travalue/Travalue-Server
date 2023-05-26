package com.deploy.Travalue.travel.infrastructure;

import com.deploy.Travalue.travel.domain.likeTavel.LikeTravel;
import com.deploy.Travalue.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeTravelRepository extends JpaRepository<LikeTravel, Long> {
    int countLikeTravelByUser(User user);
}