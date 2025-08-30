package com.moeum.moeum.api.ledger.investSummary.repository;

import com.moeum.moeum.domain.InvestSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface InvestSummaryRepository extends JpaRepository<InvestSummary, Long>, InvestSummaryQueryRepository {
}
