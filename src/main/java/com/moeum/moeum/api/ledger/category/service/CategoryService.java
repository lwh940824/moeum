package com.moeum.moeum.api.ledger.category.service;

import com.moeum.moeum.api.ledger.category.mapper.CategoryMapper;
import com.moeum.moeum.api.ledger.category.dto.CategoryCreateRequestDto;
import com.moeum.moeum.api.ledger.category.dto.CategoryResponseDto;
import com.moeum.moeum.api.ledger.category.dto.CategoryUpdateRequestDto;
import com.moeum.moeum.api.ledger.category.repository.CategoryRepository;
import com.moeum.moeum.api.ledger.user.service.UserService;
import com.moeum.moeum.domain.Category;
import com.moeum.moeum.global.exception.CustomException;
import com.moeum.moeum.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final UserService userService;

    public List<CategoryResponseDto> findAllByUserId(Long userId) {
        return categoryRepository.findAllByUserId(userId).stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    public CategoryResponseDto findById(Long categoryId) {
        return categoryMapper.toDto(
                categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CATEGORY))
        );
    }

    @Transactional
    public CategoryResponseDto create(Long userId, CategoryCreateRequestDto categoryCreateRequestDto) {
        categoryRepository.findByUserIdAndName(userId, categoryCreateRequestDto.name())
                .ifPresent(category -> {throw new CustomException(ErrorCode.EXISTS_CATEGORY);});

        Category category = categoryMapper.toEntity(categoryCreateRequestDto);
        category.assignUser(userService.getEntity(userId));

        if (categoryCreateRequestDto.parentId() != null) {
            Category parentCategory = getEntity(userId, categoryCreateRequestDto.parentId());
            category.changeParent(parentCategory);
        }

        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Transactional
    public CategoryResponseDto update(Long userId, Long categoryId, CategoryUpdateRequestDto categoryUpdateRequestDto) {
        Category category = getEntity(userId, categoryId);

        category.update(
                categoryUpdateRequestDto.name(),
                categoryUpdateRequestDto.categoryType(),
                categoryUpdateRequestDto.imageUrl()
        );

        if (categoryUpdateRequestDto.parentId() != null) {
            Category parentCategory = getEntity(userId, categoryUpdateRequestDto.parentId());
            category.changeParent(parentCategory);
        }

        return categoryMapper.toDto(category);
    }

    @Transactional
    public void delete(Long userId, Long categoryId) {
        Category category = getEntity(userId, categoryId);
        categoryRepository.delete(category);
    }

    public Category getEntity(Long userId, Long categoryId) {
        return categoryRepository.findByUserIdAndId(userId, categoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CATEGORY));
    }
}