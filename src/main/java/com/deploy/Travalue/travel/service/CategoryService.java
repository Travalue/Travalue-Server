package com.deploy.Travalue.travel.service;

import com.deploy.Travalue.exception.model.NotFoundException;
import com.deploy.Travalue.travel.controller.dto.request.CategoryRequestDto;
import com.deploy.Travalue.travel.domain.Category;
import com.deploy.Travalue.travel.infrastructure.CategoryRepository;
import com.deploy.Travalue.user.domain.User;
import com.deploy.Travalue.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public void createCategory(final Long userId, final String imagePath, final CategoryRequestDto request) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));
        final Category category = categoryRepository.save(Category.newInstance(
                user,
                request.getTitle(),
                imagePath,
                request.getSubject(),
                request.getRegion()
        ));
    }
}
