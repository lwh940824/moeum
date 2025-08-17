package com.moeum.moeum.api.ledger.investSetting.repository;

import com.moeum.moeum.domain.InvestSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvestSettingRepository extends JpaRepository<InvestSetting, Long> {
}
