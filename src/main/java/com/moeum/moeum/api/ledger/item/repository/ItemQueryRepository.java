package com.moeum.moeum.api.ledger.item.repository;

import com.moeum.moeum.domain.Item;

import java.util.List;
import java.util.Optional;

public interface ItemQueryRepository {
    List<Item> findAllByUserId(Long userId);

    Optional<Item> findByUserIdAndId(Long userId, Long itemId);
}
