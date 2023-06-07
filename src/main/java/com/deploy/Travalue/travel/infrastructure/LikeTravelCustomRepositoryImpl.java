package com.deploy.Travalue.travel.infrastructure;

import com.deploy.Travalue.travel.domain.QLikeTravel;
import com.deploy.Travalue.user.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.querydsl.core.types.ExpressionUtils.count;

@RequiredArgsConstructor
public class LikeTravelCustomRepositoryImpl implements LikeTravelCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    QLikeTravel likeTravel = QLikeTravel.likeTravel;

    @Override
    public int countLikeTravelByUser(User user) {
        return jpaQueryFactory
                .select(count(likeTravel.travel))
                .from(likeTravel)
                .where(likeTravel.user.eq(user))
                .where(likeTravel.travel.isPublic.eq(true))
                .where(likeTravel.travel.isDeleted.eq(false))
                .fetchOne()
                .intValue();
    }
}