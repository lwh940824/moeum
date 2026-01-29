package com.moeum.moeum.api.ledger.itemPlan.service;

import com.moeum.moeum.api.ledger.category.service.CategoryService;
import com.moeum.moeum.api.ledger.item.service.ItemService;
import com.moeum.moeum.api.ledger.itemPlan.dto.ItemPlanCreateRequestDto;
import com.moeum.moeum.api.ledger.itemPlan.dto.ItemPlanResponseDto;
import com.moeum.moeum.api.ledger.itemPlan.dto.ItemPlanUpdateRequestDto;
import com.moeum.moeum.api.ledger.itemPlan.mapper.ItemPlanMapper;
import com.moeum.moeum.api.ledger.itemPlan.repository.ItemPlanRepository;
import com.moeum.moeum.api.ledger.payment.service.PaymentService;
import com.moeum.moeum.api.ledger.user.service.UserService;
import com.moeum.moeum.domain.*;
import com.moeum.moeum.type.RecurringType;
import com.moeum.moeum.global.exception.CustomException;
import com.moeum.moeum.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemPlanService {

    private final ItemPlanRepository itemPlanRepository;
    private final ItemPlanMapper itemPlanMapper;
    private final ItemService itemService;
    private final CategoryService categoryService;
    private final PaymentService paymentService;
    private final UserService userService;

    @Transactional(readOnly = true)
    public List<ItemPlanResponseDto> getItemPlanList(Long userId) {
        return itemPlanRepository.findAllByUserId(userId).stream()
                .map(itemPlanMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public ItemPlanResponseDto getItemPlan(Long userId, Long itemPlanId) {
        return itemPlanMapper.toDto(getEntity(userId, itemPlanId));
    }

    @Transactional
    public ItemPlanResponseDto create(Long userId, ItemPlanCreateRequestDto itemPlanRequestDto) {
        Item item = itemService.getEntity(userId, itemPlanRequestDto.itemId());
        User user = userService.getEntity(userId);

        ItemPlan itemPlan = itemPlanMapper.toEntity(itemPlanRequestDto);
        itemPlan.reflectItem(item);
        itemPlan.addItem(item);
        itemPlan.assignUser(user);

        itemPlanRepository.save(itemPlan);

        if (itemPlan.getRecurringStartDate().isBefore(LocalDate.now())) {
            createPastItems(itemPlan);
        }

        return itemPlanMapper.toDto(itemPlan);
    }

    @Transactional
    public ItemPlanResponseDto update(Long userId, Long itemPlanId, ItemPlanUpdateRequestDto itemPlanUpdateRequestDto) {
        ItemPlan itemPlan = getEntity(userId, itemPlanId);
        Category category = categoryService.getEntity(userId, itemPlanUpdateRequestDto.categoryId());
        Payment payment = paymentService.getEntity(userId, itemPlanUpdateRequestDto.paymentId());

        itemPlan.update(
                itemPlanUpdateRequestDto.recurringType(),
                itemPlanUpdateRequestDto.recurringStartDate(),
                itemPlanUpdateRequestDto.recurringEndDate(),
                itemPlanUpdateRequestDto.amount(),
                itemPlanUpdateRequestDto.memo(),
                category,
                payment
        );

        return itemPlanMapper.toDto(itemPlan);
    }

    @Transactional
    public void delete(Long userId, Long itemPlanId) {
        ItemPlan itemPlan = getEntity(userId, itemPlanId);
        itemPlanRepository.delete(itemPlan);
    }

    @Transactional(readOnly = true)
    public ItemPlan getEntity(Long userId, Long itemPlanId) {
        return itemPlanRepository.findByUserIdAndId(userId, itemPlanId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ITEM_PLAN));
    }

    private void createPastItems(ItemPlan itemPlan) {
        LocalDate today = LocalDate.now();
        LocalDate endDate = itemPlan.getRecurringEndDate();
        LocalDate startDate = itemPlan.getRecurringStartDate();
        LocalDate limitDay = endDate.isBefore(today) ? endDate : today;

        if (startDate.isAfter(limitDay)) {
            return;
        }

        Set<LocalDate> existingOccurredAt = itemPlan.getItemList().stream()
                .map(item -> item.getOccurredAt().toLocalDate())
                .collect(Collectors.toSet());

        LocalDate cursor = startDate;
        while (!cursor.isAfter(limitDay)) {
            if (!existingOccurredAt.contains(cursor)) {
                itemService.createFromItemPlan(itemPlan, cursor.atStartOfDay());
            }
            cursor = nextOccurrence(cursor, itemPlan.getRecurringType());
        }
    }

    private LocalDate nextOccurrence(LocalDate current, RecurringType recurringType) {
        return switch (recurringType) {
            case DAY -> current.plusDays(1);
            case WEEK -> current.plusWeeks(1);
            case MONTH -> current.plusMonths(1);
        };
    }
}
