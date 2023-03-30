package com.deploy.Travalue.user.service.myTrip;

import com.deploy.Travalue.exception.model.NotFoundException;
import com.deploy.Travalue.user.controller.dto.AddTripRequestDto;
import com.deploy.Travalue.user.domain.User;
import com.deploy.Travalue.user.domain.myTrip.MyTrip;
import com.deploy.Travalue.user.infrastructure.UserRepository;
import com.deploy.Travalue.user.infrastructure.myTrip.MyTripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MyTripService {
    private final MyTripRepository myTripRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createMyTrip(final Long userId, final AddTripRequestDto request) {
        final User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));
        final MyTrip myTrip = myTripRepository.save(MyTrip.newInstance(
                user,
                request.getEmoji(),
                request.getTravelTitle()
        ));
    }
}
