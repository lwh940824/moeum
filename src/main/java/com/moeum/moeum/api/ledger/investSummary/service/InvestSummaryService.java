package com.moeum.moeum.api.ledger.investSummary.service;

import com.moeum.moeum.api.ledger.investSummary.repository.InvestSummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class InvestSummaryService {
    private final InvestSummaryRepository investSummaryRepository;
}
