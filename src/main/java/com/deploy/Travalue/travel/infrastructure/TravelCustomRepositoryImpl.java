package com.deploy.Travalue.travel.infrastructure;

import com.deploy.Travalue.travel.domain.QCategory;
import com.deploy.Travalue.travel.domain.QTravel;
import com.deploy.Travalue.user.controller.dto.SharedTravelDto;
import com.deploy.Travalue.user.domain.User;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TravelCustomRepositoryImpl implements TravelCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    QTravel travel = QTravel.travel;
    QCategory category = QCategory.category;

    @Override
    public List<SharedTravelDto> findSharedTravelList(User user) {
        return jpaQueryFactory
                .select(Projections.constructor(SharedTravelDto.class,
                        travel.category.id.as("categoryId"),
                        travel.category.title,
                        travel.category.subject,
                        travel.category.thumbnail,
                        ExpressionUtils.count(travel.category)
                ))
                .from(travel)
                .join(travel.category, category)
                .groupBy(category)
                .where(travel.user.eq(user))
                .where(travel.isDeleted.eq(false))
                .fetch();
    }
}