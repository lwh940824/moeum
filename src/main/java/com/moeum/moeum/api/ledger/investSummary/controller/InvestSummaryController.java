package com.moeum.moeum.api.ledger.investSummary.controller;

import com.moeum.moeum.api.ledger.investSummary.dto.InvestSummaryResponseDto;
import com.moeum.moeum.api.ledger.investSummary.service.InvestSummaryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "투자 집계")
@RestController
@RequestMapping("/api/invest-summary")
@RequiredArgsConstructor
public class InvestSummaryController {

    private final InvestSummaryService investSummaryService;

    @GetMapping("/{investSettingId}")
    public ResponseEntity<List<InvestSummaryResponseDto>> getInvestSummaryList(@PathVariable Long investSettingId) {
        return ResponseEntity.ok(investSummaryService.getInvestSummaryList(investSettingId));
    }
}
