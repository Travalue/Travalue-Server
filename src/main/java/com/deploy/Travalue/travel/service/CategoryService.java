package com.deploy.Travalue.travel.service;

import com.deploy.Travalue.exception.model.NotFoundException;
import com.deploy.Travalue.exception.model.UnauthorizedException;
import com.deploy.Travalue.external.client.aws.S3Service;
import com.deploy.Travalue.travel.controller.dto.request.CategoryRequestDto;
import com.deploy.Travalue.travel.domain.Category;
import com.deploy.Travalue.travel.infrastructure.CategoryRepository;
import com.deploy.Travalue.travel.infrastructure.TravelRepository;
import com.deploy.Travalue.travel.service.dto.response.CategoryListResponseDto;
import com.deploy.Travalue.user.domain.User;
import com.deploy.Travalue.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final S3Service s3Service;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TravelRepository travelRepository;

    @Transactional
    public void create(final Long userId, final String imagePath, final CategoryRequestDto request) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));
        categoryRepository.save(Category.newInstance(
                user,
                request.getTitle(),
                imagePath,
                request.getSubject(),
                request.getRegion()
        ));
    }

    @Transactional
    public void update(final Long userId, final Long categoryId, final String imagePath, final CategoryRequestDto request) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 카테고리입니다."));

        if (!userId.equals(category.getUser().getId())) {
            throw new UnauthorizedException("해당 카테고리에 권한이 없습니다.");
        }

        s3Service.deleteFile(category.getThumbnail());

        category.update(user, request.getTitle(), imagePath, request.getSubject(), request.getRegion());

    }

    @Transactional
    public void delete(final Long userId, final Long categoryId) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 카테고리입니다."));

        if (!userId.equals(category.getUser().getId())) {
            throw new UnauthorizedException("해당 카테고리에 권한이 없습니다.");
        }

        s3Service.deleteFile(category.getThumbnail());

        categoryRepository.delete(category);
    }

    @Transactional
    public List<CategoryListResponseDto> getList(final Long userId) {

        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        List<Category> categories = categoryRepository.findAllByUser(user);

        List<CategoryListResponseDto> response = new ArrayList<>();
        for (Category category : categories) {
            Long count = travelRepository.countByCategoryId(category.getId());
            response.add(CategoryListResponseDto.of(category.getTitle(), count));
        }

        return response;
    }
}
