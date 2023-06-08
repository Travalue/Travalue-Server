package com.deploy.Travalue.travel.infrastructure;

import com.deploy.Travalue.user.domain.User;

public interface LikeTravelCustomRepository {
    int countLikeTravelByUser(User user);
}