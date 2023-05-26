package com.deploy.Travalue.user.infrastructure.block;

import com.deploy.Travalue.user.domain.User;
import com.deploy.Travalue.user.domain.block.BlockUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlockUserRepository extends JpaRepository<BlockUser, Long> {
    Optional<BlockUser> findBlockUsersByBlockUserAndBlockedUser(User blockUser, User blockedUser);
}
