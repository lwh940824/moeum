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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final UserService userService;

    @Transactional(readOnly = true)
    public List<CategoryResponseDto> getCategoryList(Long userId) {
        return categoryRepository.findAllByUserIdAndGroupIdIsNotNull(userId).stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoryResponseDto findById(Long userId, Long categoryId) {
        return categoryMapper.toDto(getEntity(userId, categoryId));
    }

    @Transactional(readOnly = true)
    public List<CategoryResponseDto> getCategoryGroupList(Long userId) {
        return categoryRepository.findAllByUserIdAndGroupIdIsNull(userId).stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Transactional
    public CategoryResponseDto create(Long userId, CategoryCreateRequestDto categoryCreateRequestDto) {
        categoryRepository.findByUserIdAndName(userId, categoryCreateRequestDto.name())
                .ifPresent(category -> {throw new CustomException(ErrorCode.EXISTS_CATEGORY);});

        Category category = categoryMapper.toEntity(categoryCreateRequestDto);
        category.assignUser(userService.getEntity(userId));

        if (categoryCreateRequestDto.groupId() != null) {
            Category categoryGroup = getEntity(userId, categoryCreateRequestDto.groupId());
            category.changeGroupId(categoryGroup);
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

        if (categoryUpdateRequestDto.groupId() == null) {
            category.changeGroupId(null);
        } else {
            Category categoryGroup = getEntity(userId, categoryUpdateRequestDto.groupId());

            // 자기 자신 설정 금지
            if (categoryGroup.getId().equals(categoryUpdateRequestDto.groupId())) throw new CustomException(ErrorCode.CATEGORY_ERROR);

            category.changeGroupId(categoryGroup);
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