package com.moeum.moeum.api.ledger.investSetting.mapper;

import com.moeum.moeum.api.ledger.investSetting.dto.InvestSettingCreateDto;
import com.moeum.moeum.api.ledger.investSetting.dto.InvestSettingResponseDto;
import com.moeum.moeum.domain.InvestSetting;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InvestSettingMapper {

//    @Mapping(source = "category", target = "categoryResponseDto")
    InvestSettingResponseDto toDto(InvestSetting investSetting);

    InvestSetting toEntity(InvestSettingCreateDto investSettingCreateDto);
}
