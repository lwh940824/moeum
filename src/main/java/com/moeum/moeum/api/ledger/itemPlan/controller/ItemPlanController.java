package com.moeum.moeum.api.ledger.itemPlan.controller;

import com.moeum.moeum.api.ledger.itemPlan.dto.ItemPlanCreateRequestDto;
import com.moeum.moeum.api.ledger.itemPlan.dto.ItemPlanResponseDto;
import com.moeum.moeum.api.ledger.itemPlan.repository.ItemPlanRepository;
import com.moeum.moeum.api.ledger.itemPlan.service.ItemPlanService;
import com.moeum.moeum.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "아이템")
@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemPlanController {

    private final ItemPlanService itemPlanService;
    private final ItemPlanRepository itemPlanRepository;

    @GetMapping
    public ResponseEntity<List<ItemPlanResponseDto>> getItemPlanList(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok(itemPlanService.getItemPlanList(userDetails.getId()));
    }

    @GetMapping("/{itemPlanId}")
    public ResponseEntity<ItemPlanResponseDto> getItemPlan(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long itemPlanId
    ) {
        return ResponseEntity.ok(itemPlanService.getItemPlan(userDetails.getId(), itemPlanId));
    }

    @PostMapping
    public ResponseEntity<ItemPlanResponseDto> createItemPlan(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody ItemPlanCreateRequestDto itemPlanCreateRequestDto
    ) {
        ItemPlanResponseDto itemPlanResponseDto = itemPlanService.create(userDetails.getId(), itemPlanCreateRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemPlanResponseDto);
    }
}