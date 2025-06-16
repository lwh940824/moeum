package com.moeum.moeum.api.ledger.item.repository;

import com.moeum.moeum.api.ledger.categoryGroup.repository.ItemQueryRepository;
import com.moeum.moeum.domain.Item;
import com.moeum.moeum.domain.QCategory;
import com.moeum.moeum.domain.QCategoryGroup;
import com.moeum.moeum.domain.QItem;
import com.moeum.moeum.global.queryDSL.QuerydslConfig;
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
        QCategory category = QCategory.category;
        QCategoryGroup categoryGroup = QCategoryGroup.categoryGroup;

        return queryFactory
                .selectFrom(item)
                .join(item.category, category).fetchJoin()
                .join(category.categoryGroup, categoryGroup).fetchJoin()
                .where(
                        categoryGroup.user.id.eq(userId)
                ).fetch();
    }

    @Override
    public Optional<Item> findByUserIdAndId(Long userId, Long itemId) {
        QItem item = QItem.item;
        QCategory category = QCategory.category;
        QCategoryGroup categoryGroup = QCategoryGroup.categoryGroup;

        return Optional.ofNullable(queryFactory
                .selectFrom(item)
                .join(item.category, category).fetchJoin()
                .join(category.categoryGroup, categoryGroup).fetchJoin()
                .where(
                        categoryGroup.user.id.eq(userId)
                                .and(item.id.eq(itemId))
                ).fetchOne()
        );
    }
}
