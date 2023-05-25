package com.deploy.Travalue.travel.infrastructure;

import com.deploy.Travalue.travel.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
