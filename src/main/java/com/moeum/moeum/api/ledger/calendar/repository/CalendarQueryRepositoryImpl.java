package com.moeum.moeum.api.ledger.calendar.repository;

import com.moeum.moeum.api.ledger.calendar.dto.CalendarSummaryResponseDto;
import com.moeum.moeum.domain.QCategory;
import com.moeum.moeum.domain.QCategoryGroup;
import com.moeum.moeum.domain.QItem;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CalendarQueryRepositoryImpl implements CalendarQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<CalendarSummaryResponseDto> findItemsByUserAndPeriod(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        QCategoryGroup categoryGroup = QCategoryGroup.categoryGroup;
        QCategory category = QCategory.category;
        QItem item = QItem.item;

        return queryFactory
                .select(Projections.constructor(
                        CalendarSummaryResponseDto.class,
                        item.id,
                        categoryGroup.id,
                        categoryGroup.name,
                        category.id,
                        category.name,
                        item.occurred_at,
                        item.amount,
                        item.memo
                ))
                .join(item.category, category)
                .join(category.categoryGroup, categoryGroup)
                .where(categoryGroup.user.id.eq(userId)
                        .and(item.occurred_at.between(startDate, endDate)))
                .fetch();
    }
}
