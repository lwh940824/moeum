package com.moeum.moeum.api.ledger.item.repository;

import com.moeum.moeum.domain.Item;
import com.moeum.moeum.domain.QCategory;
import com.moeum.moeum.domain.QItem;
import com.moeum.moeum.domain.QPayment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ItemQueryRepositoryImpl implements ItemQueryRepository {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<Item> findAllByUserId(Long userId) {
        QItem item = QItem.item;
        QCategory childCategory = QCategory.category;
        QCategory parentCategory = new QCategory("parentCategory");
        QPayment payment = QPayment.payment;

        return queryFactory
                .selectFrom(item)
                .join(item.category, childCategory).fetchJoin()
                .leftJoin(childCategory.parentCategory, parentCategory).fetchJoin()
                .join(item.payment, payment).fetchJoin()
                .where(childCategory.user.id.eq(userId))
                .orderBy(item.occurredAt.desc())
                .fetch();
    }

    @Override
    public Optional<Item> findByUserIdAndId(Long userId, Long itemId) {
        QItem item = QItem.item;
        QCategory category = QCategory.category;

        return Optional.ofNullable(queryFactory
                .selectFrom(item)
                .join(item.category, category).fetchJoin()
                .where(
                        category.user.id.eq(userId)
                                .and(item.id.eq(itemId))
                ).fetchOne()
        );
    }
}
