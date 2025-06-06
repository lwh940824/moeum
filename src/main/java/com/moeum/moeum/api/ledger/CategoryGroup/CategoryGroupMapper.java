package com.moeum.moeum.api.ledger.CategoryGroup;

import com.moeum.moeum.api.ledger.CategoryGroup.dto.CategoryGroupCreateRequestDto;
import com.moeum.moeum.api.ledger.CategoryGroup.dto.CategoryGroupResponseDto;
import com.moeum.moeum.domain.CategoryGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryGroupMapper {

    @Mapping(source = "categoryGroup.categoryGroupId", target = "categoryGroupId")
    CategoryGroupResponseDto toDto(CategoryGroup categoryGroup);

    @Mapping(target = "categoryGroupId", ignore = true)
    @Mapping(target = "categoryList", ignore = true)
    CategoryGroup toEntity(CategoryGroupCreateRequestDto categoryGroupDto);
}
