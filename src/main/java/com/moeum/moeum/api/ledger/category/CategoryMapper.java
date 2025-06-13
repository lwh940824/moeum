package com.moeum.moeum.api.ledger.category;

import com.moeum.moeum.api.ledger.category.dto.CategoryCreateRequestDto;
import com.moeum.moeum.api.ledger.category.dto.CategoryResponseDto;
import com.moeum.moeum.domain.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponseDto toDto(Category category);


    Category toEntity(CategoryCreateRequestDto categoryCreateRequestDto);
}