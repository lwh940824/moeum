package com.moeum.moeum.api.ledger.investSummary.repository;

import com.moeum.moeum.domain.InvestSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvestSummaryRepository extends JpaRepository<InvestSummary, Long>, InvestSummaryQueryRepository {

    List<InvestSummary> findAllByInvestSettingId(Long investSettingId);
}
