package com.moeum.moeum.api.ledger.investSetting.mapper;

import com.moeum.moeum.api.ledger.investSummary.dto.InvestSettingResponseDto;
import com.moeum.moeum.domain.InvestSetting;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InvestSettingMapper {

    InvestSettingResponseDto toDto(InvestSetting investSetting);

    InvestSetting toEntity();
}
