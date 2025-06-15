package com.moeum.moeum.api.ledger.item;

import com.moeum.moeum.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findAllByUserId(Long userId);

    Optional<Item> findByUserIdAndId(Long userId, Long itemId);
}