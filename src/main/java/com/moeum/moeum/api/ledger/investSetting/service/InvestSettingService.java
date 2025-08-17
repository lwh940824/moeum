package com.moeum.moeum.api.ledger.investSetting.service;

import com.moeum.moeum.api.ledger.investSetting.repository.InvestSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvestSettingService {

    private final InvestSettingRepository investSettingRepository;
}

