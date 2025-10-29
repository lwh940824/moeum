package com.moeum.moeum.api.ledger.itemPlan.repository;

import com.moeum.moeum.domain.ItemPlan;

import java.util.List;
import java.util.Optional;

public interface ItemPlanQueryRepository {
    List<ItemPlan> findAllByUserId(Long userId);

    Optional<ItemPlan> findByUserIdAndId(Long userId, Long itemPlanId);
}
