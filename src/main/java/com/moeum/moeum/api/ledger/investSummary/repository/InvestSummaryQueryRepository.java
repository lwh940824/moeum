package com.moeum.moeum.api.ledger.investSummary.repository;

import com.moeum.moeum.domain.InvestSummary;

public interface InvestSummaryQueryRepository {
    Long incrementPrincipal(Long investSettingId, Integer year, Integer month, Long principal);

    InvestSummary upsertInvestSummary(Long investSettingId, Integer year, Integer month, Long principal);

    void deleteAllByInvestSettingId(Long investSettingId);
}