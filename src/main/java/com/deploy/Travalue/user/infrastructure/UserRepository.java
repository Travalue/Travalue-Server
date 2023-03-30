package com.deploy.Travalue.user.infrastructure;

import com.deploy.Travalue.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
