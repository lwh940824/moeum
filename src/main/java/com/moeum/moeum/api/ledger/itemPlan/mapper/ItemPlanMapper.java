package com.moeum.moeum.api.ledger.itemPlan.mapper;

import com.moeum.moeum.api.ledger.category.mapper.CategoryMapper;
import com.moeum.moeum.api.ledger.itemPlan.dto.ItemPlanCreateRequestDto;
import com.moeum.moeum.api.ledger.itemPlan.dto.ItemPlanResponseDto;
import com.moeum.moeum.api.ledger.payment.mapper.PaymentMapper;
import com.moeum.moeum.domain.ItemPlan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, PaymentMapper.class})
public interface ItemPlanMapper {

    @Mapping(source = "category", target = "categoryResponseDto")
    @Mapping(source = "payment", target = "paymentResponseDto")
    ItemPlanResponseDto toDto(ItemPlan itemPlan);

    @Mapping(target = "amount", ignore = true)
    @Mapping(target = "memo", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "payment", ignore = true)
    ItemPlan toEntity(ItemPlanCreateRequestDto itemPlanRequestDto);
}