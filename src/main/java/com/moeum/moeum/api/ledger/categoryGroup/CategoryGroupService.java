package com.moeum.moeum.api.ledger.categoryGroup;

import com.moeum.moeum.api.ledger.categoryGroup.dto.CategoryGroupCreateRequestDto;
import com.moeum.moeum.api.ledger.categoryGroup.dto.CategoryGroupResponseDto;
import com.moeum.moeum.api.ledger.categoryGroup.dto.CategoryGroupUpdateRequestDto;
import com.moeum.moeum.api.ledger.User.UserService;
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
                        .orElseThrow(() -> new RuntimeException("카테고리 그룹이 존재하지 않습니다."))
        );
    }

    @Transactional
    public CategoryGroupResponseDto create(Long userId, CategoryGroupCreateRequestDto categoryGroupCreateRequestDto) {
        categoryGroupRepository.findByUserIdAndName(userId, categoryGroupCreateRequestDto.name())
                .ifPresent(categoryGroup -> {throw new CustomException(ErrorCode.EXISTS_CATEGORY_GROUP);});

        CategoryGroup categoryGroup = categoryGroupMapper.toEntity(categoryGroupCreateRequestDto);
        categoryGroup.assignUser(userService.getEntity(userId));

        return categoryGroupMapper.toDto(
                categoryGroupRepository.save(categoryGroupMapper.toEntity(categoryGroupCreateRequestDto))
        );
    }

    @Transactional
    public CategoryGroupResponseDto update(Long userId, Long categoryGroupId, CategoryGroupUpdateRequestDto categoryGroupUpdateRequestDto) {
        CategoryGroup categoryGroup = categoryGroupRepository.findByUserIdAndId(userId, categoryGroupId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CATEGORY_GROUP));

        categoryGroup.update(
                categoryGroupUpdateRequestDto.name(),
                categoryGroupUpdateRequestDto.categoryType(),
                categoryGroupUpdateRequestDto.imageUrl()
        );

        return categoryGroupMapper.toDto(categoryGroup);
    }

    @Transactional
    public void delete(Long userId, Long categoryGroupId) {
        // 존재하지 않아도 예외가 발생하지 않기에 지양
        // categoryGroupRepository.deleteByUserIdAndId(userId, categoryGroupId);
        CategoryGroup categoryGroup = categoryGroupRepository.findByUserIdAndId(userId, categoryGroupId)
                .orElseThrow(() -> new CustomException(ErrorCode.EXISTS_CATEGORY_GROUP));

        categoryGroupRepository.delete(categoryGroup);
    }
}
