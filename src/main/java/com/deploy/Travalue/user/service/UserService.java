package com.deploy.Travalue.user.service;

import com.deploy.Travalue.user.controller.dto.NicknameResponseDto;
import com.deploy.Travalue.user.domain.User;
import com.deploy.Travalue.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void updateNickname(Long userId, String nickname) {
        User user = userRepository.findById(userId)
            .orElseThrow(()->new IllegalArgumentException("DB에 존재하지 않는 USER ID입니다"));

        user.updateNickname(nickname);
    }

    public NicknameResponseDto checkNickname(String nickname) {
        User user = userRepository.findByNickname(nickname).orElse(null);
        boolean isDuplicate = false;
        if(user != null){ // 중복인 경우
            isDuplicate = true;
        }
        return new NicknameResponseDto(isDuplicate);
    }
}
