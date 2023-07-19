package com.deploy.Travalue.travel.infrastructure;

import com.deploy.Travalue.travel.domain.Category;
import com.deploy.Travalue.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByUser(User user);
}
