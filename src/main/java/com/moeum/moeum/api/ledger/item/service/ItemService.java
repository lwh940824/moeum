package com.moeum.moeum.api.ledger.item.service;

import com.moeum.moeum.api.ledger.category.service.CategoryService;
import com.moeum.moeum.api.ledger.investSetting.service.InvestSettingService;
import com.moeum.moeum.api.ledger.investSummary.dto.InvestSummaryCreateDto;
import com.moeum.moeum.api.ledger.investSummary.service.InvestSummaryService;
import com.moeum.moeum.api.ledger.item.dto.ItemCreateRequestDto;
import com.moeum.moeum.api.ledger.item.dto.ItemResponseDto;
import com.moeum.moeum.api.ledger.item.dto.ItemToSummaryDto;
import com.moeum.moeum.api.ledger.item.dto.ItemUpdateRequestDto;
import com.moeum.moeum.api.ledger.item.mapper.ItemMapper;
import com.moeum.moeum.api.ledger.item.repository.ItemRepository;
import com.moeum.moeum.domain.Category;
import com.moeum.moeum.domain.Item;
import com.moeum.moeum.global.exception.CustomException;
import com.moeum.moeum.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemMapper itemMapper;
    private final ItemRepository itemRepository;
    private final CategoryService categoryService;
    private final InvestSettingService investSettingService;
    private final InvestSummaryService investSummaryService;

    public List<ItemResponseDto> findAllByUserId(Long userId) {
        return itemRepository.findAllByUserId(userId).stream().map(itemMapper::toDto).toList();
    }

    public ItemResponseDto findByUserIdAndId(Long userId, Long itemId) {
        return itemMapper.toDto(getEntity(userId, itemId));
    }

    @Transactional
    public ItemResponseDto create(Long userId, ItemCreateRequestDto itemCreateRequestDto) {
        Item item = itemMapper.toEntity(itemCreateRequestDto);

        Category category = categoryService.getEntity(userId, itemCreateRequestDto.categoryId());
        item.changeCategory(category);
        itemRepository.save(item);

        investSettingService.findByCategoryId(userId, category.getId())
                .ifPresent(investSetting ->
                        investSummaryService.create(
                                InvestSummaryCreateDto.builder().
                                        investSettingId(investSetting.getId())
                                        .year(item.getOccurred_at().getYear())
                                        .month(item.getOccurred_at().getMonthValue())
                                        .principal(item.getAmount())
                                        .build()
                        )
                );

        return itemMapper.toDto(item);
    }

    public ItemResponseDto update(Long userId, Long itemId, ItemUpdateRequestDto itemUpdateRequestDto) {
        Item item = getEntity(userId, itemId);

        // 1. 수정 필요! 연월이 바뀌는 경우는 집계가 꼬이게 됨
        // 2. 카테고리 변경 되는 경우,

        investSettingService.findByCategoryId(userId, item.getCategory().getId())
                .ifPresent(investSetting -> {
                    Long addAmount = itemUpdateRequestDto.amount() - item.getAmount();
                    investSummaryService.create(
                            InvestSummaryCreateDto.builder()
                                    .investSettingId(investSetting.getId())
                                    .year(item.getOccurred_at().getYear())
                                    .month(item.getOccurred_at().getMonthValue())
                                    .principal(addAmount)
                                    .build()
                    );
        });

        item.update(itemUpdateRequestDto.amount(), itemUpdateRequestDto.occurred_at(), itemUpdateRequestDto.memo());

        Category category = categoryService.getEntity(userId, itemUpdateRequestDto.categoryId());
        item.changeCategory(category);

        return itemMapper.toDto(item);
    }

    public void delete(Long userId, Long itemId) {
        Item item = getEntity(userId, itemId);
        itemRepository.delete(item);
    }

    public List<ItemToSummaryDto> getSummary(Long categoryId) {
        return itemRepository.findAllByCategoryId(categoryId);
    }

    public Item getEntity(Long userId, Long itemId) {
        return itemRepository.findByUserIdAndId(userId, itemId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
    }
}
