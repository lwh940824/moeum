package com.moeum.moeum.api.ledger.CategoryGroup;

import com.moeum.moeum.api.ledger.CategoryGroup.dto.CategoryGroupCreateRequestDto;
import com.moeum.moeum.api.ledger.CategoryGroup.dto.CategoryGroupResponseDto;
import com.moeum.moeum.api.ledger.CategoryGroup.dto.CategoryGroupUpdateRequestDto;
import com.moeum.moeum.api.ledger.User.UserRepository;
import com.moeum.moeum.domain.CategoryGroup;
import com.moeum.moeum.domain.User;
import com.moeum.moeum.global.exception.CustomException;
import com.moeum.moeum.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryGroupService {

    CategoryGroupMapper categoryGroupMapper;
    CategoryGroupRepository categoryGroupRepository;
    UserRepository userRepository;

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
        // 해당 유저가 만든 카테고리그룹 이름이 이미 존재하는 경우
        categoryGroupRepository.findByUserIdAndName(userId, categoryGroupCreateRequestDto.name())
                .ifPresent(categoryGroup -> {throw new CustomException(ErrorCode.EXISTS_CATEGORY_GROUP);});

        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.EXISTS_CATEGORY_GROUP));

        CategoryGroup categoryGroup = categoryGroupMapper.toEntity(categoryGroupCreateRequestDto);
        categoryGroup.assignUser(user);

        return categoryGroupMapper.toDto(
                categoryGroupRepository.save(categoryGroupMapper.toEntity(categoryGroupCreateRequestDto))
        );
    }

    @Transactional
    public CategoryGroupResponseDto update(Long categoryGroupId, CategoryGroupUpdateRequestDto categoryGroupUpdateRequestDto) {
        CategoryGroup categoryGroup = categoryGroupRepository.findByCategoryGroupId(categoryGroupId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CATEGORY_GROUP));

        categoryGroup.update(
                categoryGroupUpdateRequestDto.name(),
                categoryGroupUpdateRequestDto.categoryType(),
                categoryGroupUpdateRequestDto.imageUrl()
        );

        return categoryGroupMapper.toDto(categoryGroup);
    }

    @Transactional
    public void delete(Long categoryGroupId) {
        categoryGroupRepository.deleteById(categoryGroupId);
    }
}
