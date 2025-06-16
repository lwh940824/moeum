package com.moeum.moeum.api.ledger.categoryGroup.service;

import com.moeum.moeum.api.ledger.categoryGroup.mapper.CategoryGroupMapper;
import com.moeum.moeum.api.ledger.categoryGroup.dto.CategoryGroupCreateRequestDto;
import com.moeum.moeum.api.ledger.categoryGroup.dto.CategoryGroupResponseDto;
import com.moeum.moeum.api.ledger.categoryGroup.dto.CategoryGroupUpdateRequestDto;
import com.moeum.moeum.api.ledger.categoryGroup.repository.CategoryGroupRepository;
import com.moeum.moeum.api.ledger.user.service.UserService;
import com.moeum.moeum.domain.CategoryGroup;
import com.moeum.moeum.global.exception.CustomException;
import com.moeum.moeum.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryGroupService {

    CategoryGroupMapper categoryGroupMapper;
    CategoryGroupRepository categoryGroupRepository;
    UserService userService;

    public List<CategoryGroupResponseDto> findAllByUserId(Long userId) {
        return categoryGroupRepository.findAllByUserId(userId).stream()
                .map(categoryGroupMapper::toDto)
                .toList();
    }

    public CategoryGroupResponseDto findById(Long categoryGroupId) {
        return categoryGroupMapper.toDto(
                categoryGroupRepository.findById(categoryGroupId)
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CATEGORY_GROUP))
        );
    }

    @Transactional
    public CategoryGroupResponseDto create(Long userId, CategoryGroupCreateRequestDto categoryGroupCreateRequestDto) {
        categoryGroupRepository.findByUserIdAndName(userId, categoryGroupCreateRequestDto.name())
                .ifPresent(categoryGroup -> {throw new CustomException(ErrorCode.EXISTS_CATEGORY_GROUP);});

        CategoryGroup categoryGroup = categoryGroupMapper.toEntity(categoryGroupCreateRequestDto);
        categoryGroup.assignUser(userService.getEntity(userId));

        return categoryGroupMapper.toDto(categoryGroupRepository.save(categoryGroup));
    }

    @Transactional
    public CategoryGroupResponseDto update(Long userId, Long categoryGroupId, CategoryGroupUpdateRequestDto categoryGroupUpdateRequestDto) {
        CategoryGroup categoryGroup = getEntity(userId, categoryGroupId);

        categoryGroup.update(
                categoryGroupUpdateRequestDto.name(),
                categoryGroupUpdateRequestDto.categoryType(),
                categoryGroupUpdateRequestDto.imageUrl()
        );

        return categoryGroupMapper.toDto(categoryGroup);
    }

    @Transactional
    public void delete(Long userId, Long categoryGroupId) {
        CategoryGroup categoryGroup = getEntity(userId, categoryGroupId);
        categoryGroupRepository.delete(categoryGroup);
    }

    public CategoryGroup getEntity(Long userId, Long categoryGroupId) {
        return categoryGroupRepository.findByUserIdAndId(userId, categoryGroupId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CATEGORY_GROUP));
    }
}
