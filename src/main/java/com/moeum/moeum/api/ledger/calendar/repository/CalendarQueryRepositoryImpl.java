package com.moeum.moeum.api.ledger.calendar.repository;

import com.moeum.moeum.api.ledger.calendar.dto.CalendarSummaryResponseDto;
import com.moeum.moeum.domain.QCategory;
import com.moeum.moeum.domain.QItem;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CalendarQueryRepositoryImpl implements CalendarQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<CalendarSummaryResponseDto> findItemsByUserAndPeriod(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        QCategory category = QCategory.category;
        QItem item = QItem.item;

        return queryFactory
                .select(Projections.constructor(
                        CalendarSummaryResponseDto.class,
                        item.id,
                        category.id,
                        category.name,
                        category.id,
                        category.name,
                        item.occurredAt,
                        item.amount,
                        item.memo
                ))
                .join(category, category)
                .where(category.user.id.eq(userId)
                        .and(item.occurredAt.between(startDate, endDate)))
                .fetch();
    }
}
