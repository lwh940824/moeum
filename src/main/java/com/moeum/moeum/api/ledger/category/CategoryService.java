package com.moeum.moeum.api.ledger.category;

import com.moeum.moeum.api.ledger.category.dto.CategoryCreateRequestDto;
import com.moeum.moeum.api.ledger.category.dto.CategoryResponseDto;
import com.moeum.moeum.api.ledger.category.dto.CategoryUpdateRequestDto;
import com.moeum.moeum.api.ledger.categoryGroup.CategoryGroupService;
import com.moeum.moeum.domain.Category;
import com.moeum.moeum.domain.CategoryGroup;
import com.moeum.moeum.global.exception.CustomException;
import com.moeum.moeum.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final CategoryGroupService categoryGroupService;

    public List<CategoryResponseDto> findAllByUserId(Long userId) {
        return categoryRepository.findAllByUserId(userId).stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    public CategoryResponseDto findByUserIdAndId(Long userId, Long categoryId) {
        return categoryMapper.toDto(getEntity(userId, categoryId));
    }

    public CategoryResponseDto create(Long userId, CategoryCreateRequestDto categoryRequestDto) {
        categoryRepository.findByUserIdAndName(userId, categoryRequestDto.name())
                .ifPresent(category -> {throw new CustomException(ErrorCode.EXISTS_CATEGORY);});

        Category category = categoryMapper.toEntity(categoryRequestDto);
        CategoryGroup categoryGroup = categoryGroupService.getEntity(userId, categoryRequestDto.categoryGroupId());
        category.changeCategoryGroup(categoryGroup);

        return categoryMapper.toDto(categoryRepository.save(category));
    }

    public CategoryResponseDto update(Long userId, Long categoryId,CategoryUpdateRequestDto categoryUpdateRequestDto) {
        Category category = getEntity(userId, categoryId);

        CategoryGroup categoryGroup = categoryGroupService.getEntity(userId, categoryId);

        category.update(
                categoryUpdateRequestDto.name(),
                categoryUpdateRequestDto.imageUrl(),
                categoryUpdateRequestDto.ynType(),
                categoryUpdateRequestDto.recurringType(),
                categoryUpdateRequestDto.recurringStartDt(),
                categoryUpdateRequestDto.recurringEntDt()
        );

        category.changeCategoryGroup(categoryGroup);

        return categoryMapper.toDto(category);
    }

    public void delete(Long userId, Long categoryId) {
        Category category = getEntity(userId, categoryId);
        categoryRepository.delete(category);
    }

    public Category getEntity(Long userId, Long categoryId) {
        return categoryRepository.findByUserIdAndId(userId, categoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CATEGORY));
    }
}
