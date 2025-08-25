package com.moeum.moeum.api.ledger.item.repository;

import com.moeum.moeum.api.ledger.item.dto.ItemToSummaryDto;
import com.moeum.moeum.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemQueryRepository {

    @Query("""
        SELECT  new com.moeum.moeum.api.ledger.item.dto.ItemToSummaryDto(
            YEAR(i.occurred_at),
            MONTH(i.occurred_at),
            SUM(i.amount)
        )
        FROM Item i
        WHERE i.category.id = :categoryId
        GROUP BY YEAR(i.occurred_at), MONTH(i.occurred_at)
        ORDER BY YEAR(i.occurred_at), MONTH(i.occurred_at)
    """)
    List<ItemToSummaryDto> findAllByCategoryId(@Param("categoryId") Long categoryId);
}