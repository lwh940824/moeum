package com.moeum.moeum.api.ledger.item.service;

import com.moeum.moeum.api.ledger.category.service.CategoryService;
import com.moeum.moeum.api.ledger.investSetting.repository.InvestSettingRepository;
import com.moeum.moeum.api.ledger.investSummary.dto.InvestSummaryCreateDto;
import com.moeum.moeum.api.ledger.investSummary.service.InvestSummaryService;
import com.moeum.moeum.api.ledger.item.dto.ItemCreateRequestDto;
import com.moeum.moeum.api.ledger.item.dto.ItemResponseDto;
import com.moeum.moeum.api.ledger.item.dto.ItemResponseDto.ItemCategoryDto;
import com.moeum.moeum.api.ledger.item.dto.ItemResponseDto.ItemPaymentDto;
import com.moeum.moeum.api.ledger.item.dto.ItemToSummaryDto;
import com.moeum.moeum.api.ledger.item.dto.ItemUpdateRequestDto;
import com.moeum.moeum.api.ledger.item.mapper.ItemMapper;
import com.moeum.moeum.api.ledger.item.repository.ItemRepository;
import com.moeum.moeum.api.ledger.payment.service.PaymentService;
import com.moeum.moeum.api.ledger.user.service.UserService;
import com.moeum.moeum.domain.Category;
import com.moeum.moeum.domain.Item;
import com.moeum.moeum.domain.Payment;
import com.moeum.moeum.domain.User;
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
    private final PaymentService paymentService;
    private final UserService userService;
    private final InvestSummaryService investSummaryService;
    private final InvestSettingRepository investSettingRepository;

    @Transactional(readOnly = true)
    public List<ItemResponseDto> findAllByUserId(Long userId) {
        return itemRepository.findAllByUserId(userId).stream().map(item -> {
                    ItemCategoryDto childCategoryDto = ItemCategoryDto.builder()
                            .categoryId(item.getCategory().getId())
                            .name(item.getCategory().getName())
                            .imageUrl(item.getCategory().getImageUrl())
                            .build();

                    ItemCategoryDto parentCategoryDto = item.getCategory().getParentCategory() == null ? null :
                            ItemCategoryDto.builder()
                                    .categoryId(item.getCategory().getParentCategory().getId())
                                    .name(item.getCategory().getParentCategory().getName())
                                    .imageUrl(item.getCategory().getParentCategory().getImageUrl())
                                    .build();

                    return ItemResponseDto.builder()
                            .itemId(item.getId())
                            .amount(item.getAmount())
                            .occurredAt(item.getOccurredAt())
                            .memo(item.getMemo())
                            .childCategory(childCategoryDto)
                            .parentCategory(parentCategoryDto)
                            .payment(ItemPaymentDto.builder()
                                    .paymentId(item.getPayment().getId())
                                    .name(item.getPayment().getName())
                                    .paymentType(item.getPayment().getPaymentType())
                                    .build())
                            .build();
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public ItemResponseDto findByUserIdAndId(Long userId, Long itemId) {
        Item item = getEntity(userId, itemId);

        ItemCategoryDto childCategoryDto = ItemCategoryDto.builder()
                .categoryId(item.getCategory().getId())
                .name(item.getCategory().getName())
                .imageUrl(item.getCategory().getImageUrl())
                .build();

        ItemCategoryDto parentCategoryDto = item.getCategory().getParentCategory() == null ? null :
                ItemCategoryDto.builder()
                        .categoryId(item.getCategory().getParentCategory().getId())
                        .name(item.getCategory().getParentCategory().getName())
                        .imageUrl(item.getCategory().getParentCategory().getImageUrl())
                        .build();

        return ItemResponseDto.builder()
                .itemId(item.getId())
                .amount(item.getAmount())
                .occurredAt(item.getOccurredAt())
                .memo(item.getMemo())
                .childCategory(childCategoryDto)
                .parentCategory(parentCategoryDto)
                .payment(ItemPaymentDto.builder()
                        .paymentId(item.getPayment().getId())
                        .name(item.getPayment().getName())
                        .paymentType(item.getPayment().getPaymentType())
                        .build())
                .build();
    }

    @Transactional
    public ItemResponseDto create(Long userId, ItemCreateRequestDto itemCreateRequestDto) {
        Item item = itemMapper.toEntity(itemCreateRequestDto);

        Category category = categoryService.getEntity(userId, itemCreateRequestDto.categoryId());
        Payment payment = paymentService.getEntity(userId, itemCreateRequestDto.paymentId());
        User user = userService.getEntity(userId);
        item.changeCategory(category);
        item.changePayment(payment);
        item.assignUser(user);
        itemRepository.save(item);

        investSettingRepository.findByUserIdAndCategoryId(userId, category.getId())
                .ifPresent(investSetting ->
                        investSummaryService.create(
                                InvestSummaryCreateDto.builder().
                                        investSettingId(investSetting.getId())
                                        .year(item.getOccurredAt().getYear())
                                        .month(item.getOccurredAt().getMonthValue())
                                        .principal(item.getAmount())
                                        .build()
                        )
                );

        ItemCategoryDto childCategoryDto = ItemCategoryDto.builder()
                .categoryId(item.getCategory().getId())
                .name(item.getCategory().getName())
                .imageUrl(item.getCategory().getImageUrl())
                .build();

        ItemCategoryDto parentCategoryDto = item.getCategory().getParentCategory() == null ? null :
                ItemCategoryDto.builder()
                        .categoryId(item.getCategory().getParentCategory().getId())
                        .name(item.getCategory().getParentCategory().getName())
                        .imageUrl(item.getCategory().getParentCategory().getImageUrl())
                        .build();

        return ItemResponseDto.builder()
                .itemId(item.getId())
                .amount(item.getAmount())
                .occurredAt(item.getOccurredAt())
                .memo(item.getMemo())
                .childCategory(childCategoryDto)
                .parentCategory(parentCategoryDto)
                .payment(ItemPaymentDto.builder()
                        .paymentId(item.getPayment().getId())
                        .name(item.getPayment().getName())
                        .paymentType(item.getPayment().getPaymentType())
                        .build())
                .build();
    }

    @Transactional
    public ItemResponseDto update(Long userId, Long itemId, ItemUpdateRequestDto itemUpdateRequestDto) {
        Item item = getEntity(userId, itemId);

        // 존재하면 무조건 기존 item 금액 빼기
        investSettingRepository.findByUserIdAndCategoryId(userId, item.getCategory().getId())
                .ifPresent(investSetting ->
                    investSummaryService.create(
                            InvestSummaryCreateDto.builder()
                                    .investSettingId(investSetting.getId())
                                    .year(item.getOccurredAt().getYear())
                                    .month(item.getOccurredAt().getMonthValue())
                                    .principal(item.getAmount() * -1)
                                    .build()
                    ));

        // 현재 request만큼 investSummary 업서트
        investSettingRepository.findByUserIdAndCategoryId(userId, itemUpdateRequestDto.categoryId())
                .ifPresent(investSetting ->
                        investSummaryService.create(
                                InvestSummaryCreateDto.builder()
                                        .investSettingId(investSetting.getId())
                                        .year(itemUpdateRequestDto.occurredAt().getYear())
                                        .month(itemUpdateRequestDto.occurredAt().getMonthValue())
                                        .principal(itemUpdateRequestDto.amount())
                                        .build()
                        )
                );

        item.update(itemUpdateRequestDto.amount(), itemUpdateRequestDto.occurredAt(), itemUpdateRequestDto.memo());

        Category category = categoryService.getEntity(userId, itemUpdateRequestDto.categoryId());
        Payment payment = paymentService.getEntity(userId, itemUpdateRequestDto.paymentId());
        item.changeCategory(category);
        item.changePayment(payment);

        ItemCategoryDto childCategoryDto = ItemCategoryDto.builder()
                .categoryId(item.getCategory().getId())
                .name(item.getCategory().getName())
                .imageUrl(item.getCategory().getImageUrl())
                .build();

        ItemCategoryDto parentCategoryDto = item.getCategory().getParentCategory() == null ? null :
                ItemCategoryDto.builder()
                        .categoryId(item.getCategory().getParentCategory().getId())
                        .name(item.getCategory().getParentCategory().getName())
                        .imageUrl(item.getCategory().getParentCategory().getImageUrl())
                        .build();

        return ItemResponseDto.builder()
                .itemId(item.getId())
                .amount(item.getAmount())
                .occurredAt(item.getOccurredAt())
                .memo(item.getMemo())
                .childCategory(childCategoryDto)
                .parentCategory(parentCategoryDto)
                .payment(ItemPaymentDto.builder()
                        .paymentId(item.getPayment().getId())
                        .name(item.getPayment().getName())
                        .paymentType(item.getPayment().getPaymentType())
                        .build())
                .build();
    }

    @Transactional
    public void delete(Long userId, Long itemId) {
        Item item = getEntity(userId, itemId);

        investSettingRepository.findByUserIdAndCategoryId(userId, item.getCategory().getId())
                .ifPresent(investSetting -> {
                    investSummaryService.create(
                            InvestSummaryCreateDto.builder()
                                    .investSettingId(investSetting.getId())
                                    .year(item.getOccurredAt().getYear())
                                    .month(item.getOccurredAt().getMonthValue())
                                    .principal(item.getAmount() * -1)
                                    .build()
                    );
                });

        itemRepository.delete(item);
    }

    @Transactional(readOnly = true)
    public List<ItemToSummaryDto> getSummary(Long categoryId) {
        return itemRepository.findAllByCategoryId(categoryId);
    }

    @Transactional(readOnly = true)
    public Item getEntity(Long userId, Long itemId) {
        return itemRepository.findByUserIdAndId(userId, itemId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ITEM));
    }
}
