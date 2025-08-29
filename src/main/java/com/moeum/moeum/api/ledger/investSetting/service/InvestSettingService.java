package com.moeum.moeum.api.ledger.investSetting.service;

import com.moeum.moeum.api.ledger.investSetting.dto.InvestSettingCreateDto;
import com.moeum.moeum.api.ledger.investSetting.dto.InvestSettingResponseDto;
import com.moeum.moeum.api.ledger.investSetting.mapper.InvestSettingMapper;
import com.moeum.moeum.api.ledger.investSetting.repository.InvestSettingRepository;
import com.moeum.moeum.api.ledger.investSummary.service.InvestSummaryService;
import com.moeum.moeum.api.ledger.item.dto.ItemToSummaryDto;
import com.moeum.moeum.api.ledger.item.service.ItemService;
import com.moeum.moeum.domain.InvestSetting;
import com.moeum.moeum.global.exception.CustomException;
import com.moeum.moeum.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvestSettingService {

    private final InvestSettingMapper investSettingMapper;
    private final InvestSettingRepository investSettingRepository;
    private final ItemService itemService;
    private final InvestSummaryService investSummaryService;

    @Transactional(readOnly = true)
    public List<InvestSettingResponseDto> getInvestSettingList(Long userId) {
        return investSettingRepository.findAllByUserId(userId).stream()
                .map(investSettingMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public InvestSettingResponseDto findById(Long userId, Long investSettingId) {
        return investSettingMapper.toDto(getEntity(userId, investSettingId));
    }

    @Transactional(readOnly = true)
    public Optional<InvestSetting> findByCategoryId(Long userId, Long categoryId) {
        return investSettingRepository.findByUserIdAndCategoryId(userId, categoryId);
    }

    @Transactional
    public InvestSettingResponseDto create(Long userId, InvestSettingCreateDto investSettingCreateDto) {
        investSettingRepository.findByUserIdAndCategoryId(userId, investSettingCreateDto.categoryId())
                .ifPresent(investSetting -> {throw new CustomException(ErrorCode.CATEGORY_ERROR);});

        InvestSetting investSetting = investSettingMapper.toEntity(investSettingCreateDto);
        investSettingRepository.save(investSetting);

        // 현재 시점 이전에 생성된 해당 카테고리 가계부 집계 필요
        List<ItemToSummaryDto> summaryList = itemService.getSummary(investSettingCreateDto.categoryId());
        investSummaryService.createAll(investSetting, summaryList);

        return investSettingMapper.toDto(investSetting);
    }

    @Transactional
    public void delete(Long userId, Long id) {
        InvestSetting investSetting = getEntity(userId, id);
        investSettingRepository.delete(investSetting);
    }

    public InvestSetting getEntity(Long userId, Long id) {
        return investSettingRepository.findById(userId, id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_INVEST_SETTING));
    }
}

