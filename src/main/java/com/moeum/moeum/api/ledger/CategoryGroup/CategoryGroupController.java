package com.moeum.moeum.api.ledger.CategoryGroup;

import com.moeum.moeum.api.ledger.CategoryGroup.dto.CategoryGroupCreateRequestDto;
import com.moeum.moeum.api.ledger.CategoryGroup.dto.CategoryGroupResponseDto;
import com.moeum.moeum.api.ledger.CategoryGroup.dto.CategoryGroupUpdateRequestDto;
import com.moeum.moeum.global.security.CustomUserDetails;
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
    public ResponseEntity<List<CategoryGroupResponseDto>> getAllCategoryGroup(@RequestParam Long userId) {
        return ResponseEntity.ok(categoryGroupService.findAllByUserId(userId));
    }

    @GetMapping("/{categoryGroupId}")
    public ResponseEntity<CategoryGroupResponseDto> getCategoryGroup(@PathVariable Long categoryGroupId) {
        return ResponseEntity.ok(categoryGroupService.findById(categoryGroupId));
    }

    @PostMapping
    public ResponseEntity<CategoryGroupResponseDto> postCategoryGroup(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody CategoryGroupCreateRequestDto categoryGroupCreateRequestDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        categoryGroupService.create(userDetails.getUserId(), categoryGroupCreateRequestDto)
                );
    }

    @PutMapping("/{categoryGroupId}")
    public ResponseEntity<CategoryGroupResponseDto> updateCategoryGroup(
            @PathVariable Long categoryGroupId,
            @RequestBody CategoryGroupUpdateRequestDto categoryGroupUpdateRequestDto
    ) {
        return ResponseEntity.ok(
                categoryGroupService.update(categoryGroupId, categoryGroupUpdateRequestDto)
        );
    }

    @DeleteMapping("/{categoryGroupId}")
    public ResponseEntity<Void> deleteCategoryGroup(@PathVariable Long categoryGroupId) {
        categoryGroupService.delete(categoryGroupId);
        return ResponseEntity.noContent().build();
    }
}
