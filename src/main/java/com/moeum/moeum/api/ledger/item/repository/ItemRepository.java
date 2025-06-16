package com.moeum.moeum.api.ledger.item.repository;

import com.moeum.moeum.api.ledger.categoryGroup.repository.ItemQueryRepository;
import com.moeum.moeum.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemQueryRepository {
}