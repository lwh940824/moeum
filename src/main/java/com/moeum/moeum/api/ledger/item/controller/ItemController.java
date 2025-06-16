package com.moeum.moeum.api.ledger.item.controller;

import com.moeum.moeum.api.ledger.item.service.ItemService;
import com.moeum.moeum.api.ledger.item.dto.ItemCreateRequestDto;
import com.moeum.moeum.api.ledger.item.dto.ItemResponseDto;
import com.moeum.moeum.api.ledger.item.dto.ItemUpdateRequestDto;
import com.moeum.moeum.global.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<List<ItemResponseDto>> getAllItem(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(itemService.findAllByUserId(userDetails.getId()));
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemResponseDto> getItem(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long itemId) {
        return ResponseEntity.ok(itemService.findByUserIdAndId(userDetails.getId(), itemId));
    }

    @PostMapping
    public ResponseEntity<ItemResponseDto> createItem(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid ItemCreateRequestDto itemCreateRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(itemService.create(userDetails.getId(), itemCreateRequestDto));
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<ItemResponseDto> updateItem(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long itemId,
            @RequestBody @Valid ItemUpdateRequestDto itemUpdateRequestDto) {
        return ResponseEntity.ok(itemService.update(userDetails.getId(), itemId, itemUpdateRequestDto));
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long itemId) {
        itemService.delete(userDetails.getId(), itemId);
        return ResponseEntity.noContent().build();
    };
}