package com.moeum.moeum.api.ledger.categoryGroup;

import com.moeum.moeum.api.ledger.categoryGroup.dto.CategoryGroupCreateRequestDto;
import com.moeum.moeum.api.ledger.categoryGroup.dto.CategoryGroupResponseDto;
import com.moeum.moeum.api.ledger.categoryGroup.dto.CategoryGroupUpdateRequestDto;
import com.moeum.moeum.global.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category-group")
@RequiredArgsConstructor
public class CategoryGroupController {

    private final CategoryGroupService categoryGroupService;

    @GetMapping
    public ResponseEntity<List<CategoryGroupResponseDto>> getAllCategoryGroup(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(categoryGroupService.findAllByUserId(userDetails.getId()));
    }

    @GetMapping("/{categoryGroupId}")
    public ResponseEntity<CategoryGroupResponseDto> getCategoryGroup(@PathVariable Long categoryGroupId) {
        return ResponseEntity.ok(categoryGroupService.findById(categoryGroupId));
    }

    @PostMapping
    public ResponseEntity<CategoryGroupResponseDto> postCategoryGroup(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid CategoryGroupCreateRequestDto categoryGroupCreateRequestDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        categoryGroupService.create(userDetails.getId(), categoryGroupCreateRequestDto)
                );
    }

    @PutMapping("/{categoryGroupId}")
    public ResponseEntity<CategoryGroupResponseDto> updateCategoryGroup(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long categoryGroupId,
            @RequestBody @Valid CategoryGroupUpdateRequestDto categoryGroupUpdateRequestDto
    ) {
        return ResponseEntity.ok(
                categoryGroupService.update(userDetails.getId(), categoryGroupId, categoryGroupUpdateRequestDto)
        );
    }

    @DeleteMapping("/{categoryGroupId}")
    public ResponseEntity<Void> deleteCategoryGroup(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long categoryGroupId) {
        categoryGroupService.delete(userDetails.getId(), categoryGroupId);
        return ResponseEntity.noContent().build();
    }
}
