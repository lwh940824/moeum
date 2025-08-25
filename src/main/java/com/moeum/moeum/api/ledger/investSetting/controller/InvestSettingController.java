package com.moeum.moeum.api.ledger.investSetting.controller;

import com.moeum.moeum.api.ledger.investSetting.dto.InvestSettingCreateDto;
import com.moeum.moeum.api.ledger.investSetting.service.InvestSettingService;
import com.moeum.moeum.api.ledger.investSetting.dto.InvestSettingResponseDto;
import com.moeum.moeum.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "투자 집계 설정")
@RestController
@RequestMapping("/api/invest-setting")
@RequiredArgsConstructor
public class InvestSettingController {

    private final InvestSettingService investSettingService;

    @GetMapping
    public ResponseEntity<List<InvestSettingResponseDto>> getInvestSettingList(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(investSettingService.getInvestSettingList(userDetails.getId()));
    }

    @GetMapping("/{investSettingId}")
    public ResponseEntity<InvestSettingResponseDto> getInvestSetting(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long investSettingId) {
        return ResponseEntity.ok(investSettingService.findById(userDetails.getId(), investSettingId));
    }

    @PostMapping
    public ResponseEntity<InvestSettingResponseDto> postInvestSetting(@AuthenticationPrincipal CustomUserDetails userDetails, InvestSettingCreateDto investSettingCreateDto) {
        InvestSettingResponseDto investSettingResponseDto = investSettingService.create(userDetails.getId(), investSettingCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(investSettingResponseDto);
    }

    @DeleteMapping("/{investSettingId}")
    public ResponseEntity<Void> deleteInvestSetting(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long investSettingId) {
        investSettingService.delete(userDetails.getId(), investSettingId);
        return ResponseEntity.noContent().build();
    }
}
