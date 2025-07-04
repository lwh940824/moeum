//package com.moeum.moeum.api.ledger.category_.mapper;
//
//import com.moeum.moeum.api.ledger.category_.dto.CategoryCreateRequestDto;
//import com.moeum.moeum.api.ledger.category_.dto.CategoryResponseDto;
//import com.moeum.moeum.domain.Category;
//import org.mapstruct.Mapper;
//import org.mapstruct.ReportingPolicy;
//
//@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
//public interface CategoryMapper {
//
//    CategoryResponseDto toDto(Category category);
//
//
//    Category toEntity(CategoryCreateRequestDto categoryCreateRequestDto);
//}