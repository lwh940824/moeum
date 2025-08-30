package com.moeum.moeum.api.ledger.investSummary.service;

import com.moeum.moeum.api.ledger.investSummary.dto.InvestSummaryCreateDto;
import com.moeum.moeum.api.ledger.investSummary.dto.InvestSummaryResponseDto;
import com.moeum.moeum.api.ledger.investSummary.mapper.InvestSummaryMapper;
import com.moeum.moeum.api.ledger.investSummary.repository.InvestSummaryRepository;
import com.moeum.moeum.api.ledger.item.dto.ItemToSummaryDto;
import com.moeum.moeum.domain.InvestSetting;
import com.moeum.moeum.domain.InvestSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InvestSummaryService {
    private final InvestSummaryRepository investSummaryRepository;
    private final InvestSummaryMapper investSummaryMapper;

    @Transactional(readOnly = true)
    public List<InvestSummaryResponseDto> getInvestSummaryList(Long investSettingId) {
        return investSummaryRepository.findAllByInvestSettingId(investSettingId).stream()
                .map(investSummaryMapper::toDto)
                .toList();
    }

    @Transactional
    public void create(InvestSummaryCreateDto investSummaryCreateDto) {
        investSummaryRepository.upsertInvestSummary(
                investSummaryCreateDto.investSettingId(),
                investSummaryCreateDto.year(),
                investSummaryCreateDto.month(),
                investSummaryCreateDto.principal()
        );
    }

    @Transactional
    public void createAll(InvestSetting investSetting, List<ItemToSummaryDto> summaryList) {
        // 기존 등록된 InvestSummary 삭제
        investSummaryRepository.deleteAllByInvestSettingId(investSetting.getId());

        List<InvestSummary> investSummaryEntityList = summaryList.stream().map(summary -> {
            InvestSummary investSummary = investSummaryMapper.toEntity(
                    InvestSummaryCreateDto.builder()
                            .year(summary.year())
                            .month(summary.month())
                            .principal(summary.principal())
                            .build()
            );
            investSummary.changeInvestSetting(investSetting);
            return investSummary;
        }).toList();

        investSummaryRepository.saveAll(investSummaryEntityList);
    }

    @Transactional
    public void deleteAllByInvestSettingId(Long investSettingId) {
        investSummaryRepository.deleteAllByInvestSettingId(investSettingId);
    }
}
