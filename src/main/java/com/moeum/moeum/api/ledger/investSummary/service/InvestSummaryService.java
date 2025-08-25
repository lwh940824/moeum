package com.moeum.moeum.api.ledger.investSummary.service;

import com.moeum.moeum.api.ledger.investSummary.dto.InvestSummaryCreateDto;
import com.moeum.moeum.api.ledger.investSummary.mapper.InvestSummaryMapper;
import com.moeum.moeum.api.ledger.investSummary.repository.InvestSummaryRepository;
import com.moeum.moeum.api.ledger.item.dto.ItemToSummaryDto;
import com.moeum.moeum.domain.InvestSetting;
import com.moeum.moeum.domain.InvestSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InvestSummaryService {
    private final InvestSummaryRepository investSummaryRepository;
    private final InvestSummaryMapper investSummaryMapper;

    public void createAll(InvestSetting investSetting, List<ItemToSummaryDto> summaryList) {
        // 기존 등록된 InvestSummary 삭제
        investSummaryRepository.deleteByInvestSettingId(investSetting.getId());

        List<InvestSummary> investSummaryEntityList = summaryList.stream().map(summary -> {
            InvestSummary investSummary = investSummaryMapper.toEntity(new InvestSummaryCreateDto(
                    summary.year(),
                    summary.month(),
                    summary.principal()
            ));
            investSummary.changeInvestSetting(investSetting);
            return investSummary;
        }).toList();

        investSummaryRepository.saveAll(investSummaryEntityList);
    }
}
