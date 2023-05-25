package com.deploy.Travalue.user.service;

import com.deploy.Travalue.user.controller.dto.NicknameResponseDto;
import com.deploy.Travalue.user.controller.dto.UserBlockRequestDto;
import com.deploy.Travalue.user.domain.User;
import com.deploy.Travalue.user.domain.block.BlockUser;
import com.deploy.Travalue.user.dto.CreateUserDto;
import com.deploy.Travalue.user.infrastructure.block.BlockUserRepository;
import com.deploy.Travalue.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final BlockUserRepository blockUserRepository;

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
        Optional<User> duplicatedNickname = userRepository.findByNickname(createUserDto.getNickname());

        if (duplicatedNickname.isPresent()) {
            // TODO: 카카오에서 받아온 닉네임을 사용하는 데 보통 이름으로 설정되어 있어 중복 될 가능성 높음 (따로 처리해줘야됨)
            throw new IllegalArgumentException("중복 닉네임 입니다");
        }

        User user = User.builder()
                .createUserDto(createUserDto)
                .build();

        log.info("회원가입 성공!!");
        return userRepository.save(user);
    }

    @Transactional
    public void blockUser(Long userId, UserBlockRequestDto userBlockRequestDto) {
        // TODO: isBlocked 변수 없이 DB에서 검색해서 차단했는지 검색해서 알맞게 차단 / 해제 해주는 것도 괜찮을 듯
        Long blockUserId = userBlockRequestDto.getBlockUserUid();
        boolean isBlocked = userBlockRequestDto.isBlocked();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        User blockedUser = userRepository.findById(blockUserId)
                .orElseThrow(() -> new IllegalArgumentException("차단 할 유저가 존재 하지않습니다."));

        if (isBlocked) {
            // 차단 하는 경우
            Optional<BlockUser> alreadyBlockedUser = blockUserRepository.findBlockUsersByBlockUserAndBlockedUser(user, blockedUser);
            if (alreadyBlockedUser.isPresent()) {
                throw new IllegalArgumentException("이미 차단한 유저입니다.");
            }

            BlockUser blockUser = BlockUser.builder()
                    .blockUser(user)
                    .blockedUser(blockedUser)
                    .build();
            blockUserRepository.save(blockUser);
        } else {
            // 차단 해제 하는 경우
            BlockUser blockUser = blockUserRepository.findBlockUsersByBlockUserAndBlockedUser(user, blockedUser)
                    .orElseThrow(() -> new IllegalArgumentException("차단한 이력이 없는 유저입니다."));
            blockUserRepository.delete(blockUser);
        }
    }
}