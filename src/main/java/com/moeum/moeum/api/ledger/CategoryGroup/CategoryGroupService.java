package com.moeum.moeum.api.ledger.CategoryGroup;

import com.moeum.moeum.api.ledger.CategoryGroup.dto.CategoryGroupCreateRequestDto;
import com.moeum.moeum.api.ledger.CategoryGroup.dto.CategoryGroupResponseDto;
import com.moeum.moeum.domain.CategoryGroup;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryGroupService {

    CategoryGroupMapper categoryGroupMapper;
    CategoryGroupRepository categoryGroupRepository;

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
    public CategoryGroupResponseDto create(CategoryGroupCreateRequestDto categoryGroupCreateRequestDto) {
        return categoryGroupMapper.toDto(
                categoryGroupRepository.save(categoryGroupMapper.toEntity(categoryGroupCreateRequestDto))
        );
    }


}
