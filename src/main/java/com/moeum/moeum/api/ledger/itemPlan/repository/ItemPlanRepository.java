package com.moeum.moeum.api.ledger.itemPlan.repository;

import com.moeum.moeum.domain.ItemPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPlanRepository extends JpaRepository<ItemPlan, Long>, ItemPlanQueryRepository {
}