package com.moeum.moeum.api.ledger.category.controller;

import com.moeum.moeum.api.ledger.category.dto.CategoryCreateRequestDto;
import com.moeum.moeum.api.ledger.category.dto.CategoryResponseDto;
import com.moeum.moeum.api.ledger.category.dto.CategoryUpdateRequestDto;
import com.moeum.moeum.api.ledger.category.service.CategoryService;
import com.moeum.moeum.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "카테고리 그룹")
@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAllCategory(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(categoryService.findAllByUserId(userDetails.getId()));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponseDto> getCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(categoryService.findById(categoryId));
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDto> postCategory(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid CategoryCreateRequestDto categoryCreateRequestDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                categoryService.create(userDetails.getId(), categoryCreateRequestDto)
        );
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryResponseDto> updateCategory(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long categoryId,
            @RequestBody @Valid CategoryUpdateRequestDto categoryUpdateRequestDto
    ) {
        return ResponseEntity.ok(categoryService.update(userDetails.getId(), categoryId, categoryUpdateRequestDto));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long categoryId) {
        categoryService.delete(userDetails.getId(), categoryId);
        return ResponseEntity.noContent().build();
    }
}
