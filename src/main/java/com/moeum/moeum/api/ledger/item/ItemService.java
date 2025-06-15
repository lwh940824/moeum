package com.moeum.moeum.api.ledger.item;

import com.moeum.moeum.api.ledger.category.CategoryService;
import com.moeum.moeum.api.ledger.item.dto.ItemCreateRequestDto;
import com.moeum.moeum.api.ledger.item.dto.ItemResponseDto;
import com.moeum.moeum.api.ledger.item.dto.ItemUpdateRequestDto;
import com.moeum.moeum.domain.Category;
import com.moeum.moeum.domain.Item;
import com.moeum.moeum.global.exception.CustomException;
import com.moeum.moeum.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemMapper itemMapper;
    private final ItemRepository itemRepository;
    private final CategoryService categoryService;

    public List<ItemResponseDto> findAllByUserId(Long userId) {
        return itemRepository.findAllByUserId(userId).stream()
                .map(itemMapper::toDto)
                .toList();
    }

    public ItemResponseDto findByUserIdAndId(Long userId, Long itemId) {
        return itemMapper.toDto(getEntity(userId, itemId));
    }
    
    public ItemResponseDto create(Long userId, ItemCreateRequestDto itemCreateRequestDto) {
        Item item = itemMapper.toEntity(itemCreateRequestDto);

        Category category = categoryService.getEntity(userId, itemCreateRequestDto.categoryId());
        item.changeCategory(category);

        return itemMapper.toDto(itemRepository.save(item));
    }

    public ItemResponseDto update(Long userId, Long itemId, ItemUpdateRequestDto itemUpdateRequestDto) {
        Item item = getEntity(userId, itemId);

        item.update(
                itemUpdateRequestDto.amount(),
                itemUpdateRequestDto.occurred_at(),
                itemUpdateRequestDto.memo()
        );

        Category category = categoryService.getEntity(userId, itemUpdateRequestDto.categoryId());
        item.changeCategory(category);

        return itemMapper.toDto(item);
    }

    public void delete(Long userId, Long itemId) {
        Item item = getEntity(userId, itemId);
        itemRepository.delete(item);
    }
    
    public Item getEntity(Long userId, Long itemId) {
        return itemRepository.findByUserIdAndId(userId, itemId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
    }
}
