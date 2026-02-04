package com.moeum.moeum.api.ledger.investSummary.service;

import com.moeum.moeum.api.ledger.investSummary.dto.InvestSummaryCreateDto;
import com.moeum.moeum.api.ledger.investSummary.dto.InvestSummaryMonthDto;
import com.moeum.moeum.api.ledger.investSummary.dto.InvestSummaryResponseDto;
import com.moeum.moeum.api.ledger.investSummary.dto.InvestSummaryYearDto;
import com.moeum.moeum.api.ledger.investSummary.mapper.InvestSummaryMapper;
import com.moeum.moeum.api.ledger.investSummary.repository.InvestSummaryRepository;
import com.moeum.moeum.api.ledger.item.dto.ItemToSummaryDto;
import com.moeum.moeum.domain.InvestSetting;
import com.moeum.moeum.domain.InvestSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @Transactional(readOnly = true)
    public List<InvestSummaryYearDto> getInvestSummaryYearList(Long investSettingId) {
        List<InvestSummaryResponseDto> investSummaryList = investSummaryRepository.findAllByInvestSettingId(investSettingId).stream()
                .map(investSummaryMapper::toDto)
                .toList();

        Map<Integer, List<InvestSummaryResponseDto>> yearGroup = investSummaryList.stream()
                .collect(Collectors.groupingBy(InvestSummaryResponseDto::year));

        return yearGroup.entrySet().stream()
                .map(entry -> {
                    Integer year = entry.getKey();
                    List<InvestSummaryResponseDto> list = entry.getValue();
                    Long totalPrincipal = list.stream()
                            .map(InvestSummaryResponseDto::principal)
                            .filter(java.util.Objects::nonNull)
                            .mapToLong(Long::longValue)
                            .sum();

                    List<InvestSummaryMonthDto> months = IntStream.rangeClosed(1, 12)
                            .mapToObj(m ->
                                    list.stream()
                                            .filter(s -> s.month() == m)
                                            .findFirst()
                                            .map(s -> InvestSummaryMonthDto.builder()
                                                    .month(m)
                                                    .principal(s.principal())
                                                    .build())
                                            .orElse(
                                                    InvestSummaryMonthDto.builder()
                                                            .month(m)
                                                            .principal(0L)
                                                            .build()
                                            )
                            )
                            .toList();

                    return InvestSummaryYearDto.builder()
                            .year(year)
                            .months(months)
                            .totalPrincipal(totalPrincipal)
                            .build();
                })
                .sorted(Comparator.comparing(InvestSummaryYearDto::year))
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
