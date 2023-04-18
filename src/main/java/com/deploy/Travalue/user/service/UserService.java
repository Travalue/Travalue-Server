package com.deploy.Travalue.user.service;

import com.deploy.Travalue.user.controller.dto.NicknameResponseDto;
import com.deploy.Travalue.user.domain.User;
import com.deploy.Travalue.user.dto.CreateUserDto;
import com.deploy.Travalue.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    public void updateNickname(Long userId, String nickname) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("DB에 존재하지 않는 USER ID입니다"));

        user.updateNickname(nickname);
    }

    public NicknameResponseDto checkNickname(String nickname) {
        User user = userRepository.findByNickname(nickname).orElse(null);
        boolean isDuplicate = false;
        if (user != null) { // 중복인 경우
            isDuplicate = true;
        }
        return new NicknameResponseDto(isDuplicate);
    }

    @Transactional
    User registerUser(CreateUserDto createUserDto) {
        // 닉네임 중복 체크
        User user = userRepository.findByNickname(createUserDto.getNickname())
            .orElseThrow(
                () -> new IllegalArgumentException("중복 닉네임 입니다!!")); //여기서 중복 처리해줘야되나????????

        user = User.builder()
            .createUserDto(createUserDto)
            .build();

        log.info("회원가입 성공!!");
        return userRepository.save(user);
    }
}