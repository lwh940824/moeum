package com.moeum.moeum.api.ledger.category;

import com.moeum.moeum.api.ledger.category.dto.CategoryCreateRequestDto;
import com.moeum.moeum.api.ledger.category.dto.CategoryResponseDto;
import com.moeum.moeum.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<CategoryResponseDto> postCategory(@AuthenticationPrincipal CustomUserDetails userDetails, CategoryCreateRequestDto categoryCreateRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                categoryService.create(userDetails.getId(), categoryCreateRequestDto)
        );
    }
}
