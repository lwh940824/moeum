package com.moeum.moeum.api.ledger.investSummary.mapper;

import com.moeum.moeum.api.ledger.investSummary.dto.InvestSummaryCreateDto;
import com.moeum.moeum.api.ledger.investSummary.dto.InvestSummaryResponseDto;
import com.moeum.moeum.domain.InvestSummary;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InvestSummaryMapper {

    InvestSummaryResponseDto toDto(InvestSummary investSummary);

    InvestSummary toEntity(InvestSummaryCreateDto investSummaryCreateDto);
}
