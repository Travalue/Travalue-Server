package com.deploy.Travalue.travel.infrastructure;

import com.deploy.Travalue.travel.domain.Category;
import com.deploy.Travalue.travel.domain.Travel;
import com.deploy.Travalue.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TravelRepository extends JpaRepository<Travel, Long>, TravelCustomRepository {
    List<Travel> findTravelByUser(User user);

    List<Travel> findTravelByIsPublicTrueAndIsDeletedFalseAndSection(String section);

    List<Travel> findTravelByUserAndIsDeletedFalse(User user);

    List<Travel> findTravelByUserAndIsDeletedFalseAndIsPublicTrue(User user);

    List<Travel> findTravelByUserAndCategoryAndIsDeletedFalse(User user, Category category);

    List<Travel> findTravelByUserAndCategoryAndIsDeletedFalseAndIsPublicTrue(User user, Category category);

    List<Travel> findTravelByIsPublicTrueAndIsDeletedFalseAndSectionAndTitleContainingOrSubTitleContaining(String section, String keyword1, String keyword2);

    List<Travel> findTop3ByIsPublicTrueAndIsDeletedFalseAndSectionOrderByCreatedAtDesc(String section);

    Long countByCategoryId(Long categoryId);
}
