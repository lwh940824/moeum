package com.moeum.moeum.api.ledger.investSetting.service;

import com.moeum.moeum.api.ledger.investSetting.mapper.InvestSettingMapper;
import com.moeum.moeum.api.ledger.investSetting.repository.InvestSettingRepository;
import com.moeum.moeum.api.ledger.investSummary.dto.InvestSettingResponseDto;
import com.moeum.moeum.domain.InvestSetting;
import com.moeum.moeum.global.exception.CustomException;
import com.moeum.moeum.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvestSettingService {

    private final InvestSettingMapper investSettingMapper;
    private final InvestSettingRepository investSettingRepository;

    public List<InvestSettingResponseDto> getInvestSummaryList(Long userId) {
        return investSettingRepository.findAllByUserId(userId).stream()
                .map(investSettingMapper::toDto)
                .toList();
    }

    public InvestSettingResponseDto findById(Long userId, Long investSettingId) {
        return investSettingMapper.toDto(getEntity(userId, investSettingId));
    }

    public InvestSetting getEntity(Long userId, Long id) {
        return investSettingRepository.findById(userId, id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_INVEST_SETTING));
    }
}

