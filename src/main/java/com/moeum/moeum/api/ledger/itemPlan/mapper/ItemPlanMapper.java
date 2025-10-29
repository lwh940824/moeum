package com.moeum.moeum.api.ledger.itemPlan.mapper;

import com.moeum.moeum.api.ledger.item.mapper.ItemMapper;
import com.moeum.moeum.api.ledger.itemPlan.dto.ItemPlanCreateRequestDto;
import com.moeum.moeum.api.ledger.itemPlan.dto.ItemPlanResponseDto;
import com.moeum.moeum.domain.ItemPlan;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ItemMapper.class)
public interface ItemPlanMapper {

    ItemPlanResponseDto toDto(ItemPlan itemPlan);

    ItemPlan toEntity(ItemPlanCreateRequestDto itemPlanRequestDto);
}