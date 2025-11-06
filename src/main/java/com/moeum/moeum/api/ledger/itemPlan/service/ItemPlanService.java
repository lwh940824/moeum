package com.moeum.moeum.api.ledger.itemPlan.service;

import com.moeum.moeum.api.ledger.item.service.ItemService;
import com.moeum.moeum.api.ledger.itemPlan.dto.ItemPlanCreateRequestDto;
import com.moeum.moeum.api.ledger.itemPlan.dto.ItemPlanResponseDto;
import com.moeum.moeum.api.ledger.itemPlan.dto.ItemPlanUpdateRequestDto;
import com.moeum.moeum.api.ledger.itemPlan.mapper.ItemPlanMapper;
import com.moeum.moeum.api.ledger.itemPlan.repository.ItemPlanRepository;
import com.moeum.moeum.domain.Item;
import com.moeum.moeum.domain.ItemPlan;
import com.moeum.moeum.global.exception.CustomException;
import com.moeum.moeum.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemPlanService {

    private final ItemPlanRepository itemPlanRepository;
    private final ItemPlanMapper itemPlanMapper;
    private final ItemService itemService;

    public List<ItemPlanResponseDto> getItemPlanList(Long userId) {
        return itemPlanRepository.findAllByUserId(userId).stream()
                .map(itemPlanMapper::toDto)
                .toList();
    }

    public ItemPlanResponseDto getItemPlan(Long userId, Long itemPlanId) {
        return itemPlanMapper.toDto(getEntity(userId, itemPlanId));
    }

    public ItemPlanResponseDto create(Long userId, ItemPlanCreateRequestDto itemPlanRequestDto) {
        Item item = itemService.getEntity(userId, itemPlanRequestDto.itemId());

//        ItemPlan itemPlan = itemPlanMapper.toEntity(itemPlanRequestDto);
//        itemPlan.addItem(item);

        ItemPlan.builder()
                .recurringType(itemPlanRequestDto.recurringType())
                .recurringStartDate(itemPlanRequestDto.recurringStartDate())
                .recurringEndDate(itemPlanRequestDto.recurringEndDate())
                .amount(item.getAmount())
                .memo(item.getMemo())
                .category(item.getCategory())
                .payment(item.getPayment())
                .build();

        return itemPlanMapper.toDto(itemPlan);
    }

    public ItemPlanResponseDto update(Long userId, ItemPlanUpdateRequestDto itemPlanUpdateRequestDto) {
        return null;
    }

    public ItemPlan getEntity(Long userId, Long itemPlanId) {
        return itemPlanRepository.findByUserIdAndId(userId, itemPlanId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ITEM_PLAN));
    }
}
