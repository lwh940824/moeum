package com.moeum.moeum.api.ledger.investSummary.controller;

import com.moeum.moeum.api.ledger.investSummary.service.InvestSummaryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "투자 집계")
@RestController
@RequestMapping("/api/invest-summary")
@RequiredArgsConstructor
public class InvestSummaryController {

    private final InvestSummaryService investSummaryService;

}
