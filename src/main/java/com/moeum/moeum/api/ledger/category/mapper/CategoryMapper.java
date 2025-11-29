package com.moeum.moeum.api.ledger.category.mapper;

import com.moeum.moeum.api.ledger.category.dto.CategoryCreateRequestDto;
import com.moeum.moeum.api.ledger.category.dto.CategoryResponseDto;
import com.moeum.moeum.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    @Mapping(source = "childCategoryList", target = "children")
    CategoryResponseDto toDto(Category category);

    Category toEntity(CategoryCreateRequestDto categoryGroupDto);
}
