package com.deploy.Travalue.user.service.myTrip;

import com.deploy.Travalue.exception.model.ForbiddenException;
import com.deploy.Travalue.exception.model.NotFoundException;
import com.deploy.Travalue.user.controller.dto.myTrip.request.MyTripRequestDto;
import com.deploy.Travalue.user.controller.dto.myTrip.response.MyTripResponseDto;
import com.deploy.Travalue.user.domain.User;
import com.deploy.Travalue.user.domain.myTrip.MyTrip;
import com.deploy.Travalue.user.infrastructure.UserRepository;
import com.deploy.Travalue.user.infrastructure.myTrip.MyTripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MyTripService {
    private final MyTripRepository myTripRepository;
    private final UserRepository userRepository;

    @Transactional
    public List<MyTripResponseDto> getMyTripList(final Long userId) {
        final User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));
        List<MyTrip> myTripList = myTripRepository.findByUserId(userId);
        return myTripList.stream()
                .map(myTrip -> MyTripResponseDto.of(
                        myTrip.getId(),
                        myTrip.getEmoji(),
                        myTrip.getTravelTitle()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void createMyTrip(final Long userId, final MyTripRequestDto request) {
        final User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));
        final MyTrip myTrip = myTripRepository.save(MyTrip.newInstance(
                user,
                request.getEmoji(),
                request.getTravelTitle()
        ));
    }

    @Transactional
    public void deleteMyTrip(final Long userId, final Long myTripId) {

        final User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));
        final MyTrip myTrip = myTripRepository.findById(myTripId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 나의 여행지 입니다."));

        if (!myTrip.getUser().equals(user)) {
            throw new ForbiddenException("선택된 나의 여행지에 권한이 없습니다.");
        }

        myTripRepository.delete(myTrip);
    }
}
