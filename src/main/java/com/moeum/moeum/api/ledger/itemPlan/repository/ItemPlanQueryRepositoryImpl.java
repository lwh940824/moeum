package com.moeum.moeum.api.ledger.itemPlan.repository;

import com.moeum.moeum.domain.ItemPlan;
import com.moeum.moeum.domain.QCategory;
import com.moeum.moeum.domain.QItemPlan;
import com.moeum.moeum.domain.QPayment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ItemPlanQueryRepositoryImpl implements ItemPlanQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;
    private final QItemPlan itemPlan = QItemPlan.itemPlan;
    private final QCategory category = QCategory.category;
    private final QPayment payment = QPayment.payment;

    @Override
    public List<ItemPlan> findAllByUserId(Long userId) {
        return queryFactory
                .selectFrom(itemPlan)
                .where(
                        itemPlan.user.id.eq(userId)
                ).fetch();
    }

    @Override
    public Optional<ItemPlan> findByUserIdAndId(Long userId, Long itemPlanId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(itemPlan)
                        .join(itemPlan.category, category).fetchJoin()
                        .join(itemPlan.payment, payment).fetchJoin()
                .where(
                        itemPlan.id.eq(itemPlanId).and(itemPlan.user.id.eq(userId))
                ).fetchOne());
    }
}