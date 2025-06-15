package com.moeum.moeum.api.ledger.item;

import com.moeum.moeum.api.ledger.item.dto.ItemCreateRequestDto;
import com.moeum.moeum.api.ledger.item.dto.ItemResponseDto;
import com.moeum.moeum.domain.Item;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    ItemResponseDto toDto(Item item);

    Item toEntity(ItemCreateRequestDto itemCreateRequestDto);
}
