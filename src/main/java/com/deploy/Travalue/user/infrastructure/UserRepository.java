package com.deploy.Travalue.user.infrastructure;

import com.deploy.Travalue.user.domain.SocialInformation;
import com.deploy.Travalue.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNickname(String nickname);
    Optional<User> findBySocialInformation(SocialInformation socialInformation);
}
