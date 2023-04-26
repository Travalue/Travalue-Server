package com.deploy.Travalue.travel.controller;

import com.deploy.Travalue.common.dto.ApiResponse;
import com.deploy.Travalue.config.interceptor.Auth;
import com.deploy.Travalue.config.resolver.UserId;
import com.deploy.Travalue.exception.SuccessCode;
import com.deploy.Travalue.external.client.aws.S3Service;
import com.deploy.Travalue.travel.controller.dto.request.CategoryRequestDto;
import com.deploy.Travalue.travel.service.CategoryService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class CategoryController {

    private final S3Service s3Service;
    private final CategoryService categoryService;

    @Auth
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/category", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ApiResponse createCategory(@UserId Long userId, @ModelAttribute @Valid final CategoryRequestDto request) {
        final String imagePath = s3Service.uploadImage(request.getThumbnail(), "category");
        categoryService.createCategory(userId, imagePath, request);
        return ApiResponse.success(SuccessCode.CREATE_CATEGORY_SUCCESS);
    }
}
