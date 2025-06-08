package com.moeum.moeum.api.ledger.CategoryGroup;

import com.moeum.moeum.api.ledger.CategoryGroup.dto.CategoryGroupCreateRequestDto;
import com.moeum.moeum.api.ledger.CategoryGroup.dto.CategoryGroupResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CategoryGroupResponseDto> postCategoryGroup(@RequestBody CategoryGroupCreateRequestDto categoryGroupCreateRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        categoryGroupService.create(categoryGroupCreateRequestDto)
                );
    }
}
