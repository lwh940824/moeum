package com.moeum.moeum.api.ledger.categoryGroup.mapper;

import com.moeum.moeum.api.ledger.categoryGroup.dto.CategoryGroupCreateRequestDto;
import com.moeum.moeum.api.ledger.categoryGroup.dto.CategoryGroupResponseDto;
import com.moeum.moeum.domain.CategoryGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryGroupMapper {

    CategoryGroupResponseDto toDto(CategoryGroup categoryGroup);

    CategoryGroup toEntity(CategoryGroupCreateRequestDto categoryGroupDto);
}
