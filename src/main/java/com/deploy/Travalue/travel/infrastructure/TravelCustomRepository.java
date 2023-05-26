package com.deploy.Travalue.travel.infrastructure;

import com.deploy.Travalue.user.controller.dto.SharedTravelDto;
import com.deploy.Travalue.user.domain.User;

import java.util.List;

public interface TravelCustomRepository {
    List<SharedTravelDto> findSharedTravelList(User user);
}