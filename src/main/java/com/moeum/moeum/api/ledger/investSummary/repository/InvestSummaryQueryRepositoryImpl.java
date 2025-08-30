package com.moeum.moeum.api.ledger.investSummary.repository;

import com.moeum.moeum.domain.InvestSetting;
import com.moeum.moeum.domain.InvestSummary;
import com.moeum.moeum.domain.QInvestSummary;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class InvestSummaryQueryRepositoryImpl implements InvestSummaryQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;
    private final QInvestSummary investSummary = QInvestSummary.investSummary;

    @Override
    public Long incrementPrincipal(Long investSettingId, Integer year, Integer month, Long principal) {
        return queryFactory
                .update(investSummary)
                .set(investSummary.principal, investSummary.principal.add(principal))
                .where(
                        investSummary.investSetting.id.eq(investSettingId),
                        investSummary.year.eq(year),
                        investSummary.month.eq(month)
                ).execute();
    }

    @Override
    public InvestSummary upsertInvestSummary(Long investSettingId, Integer year, Integer month, Long principal) {
        if (incrementPrincipal(investSettingId, year, month, principal) > 0) {
            return selectInvestSummary(investSettingId, year, month);
        }

        try {
            InvestSummary createInvestSummary = InvestSummary.builder()
                    .year(year)
                    .month(month)
                    .principal(principal)
                    .build();

            createInvestSummary.changeInvestSetting(em.getReference(InvestSetting.class, investSettingId));

            em.persist(createInvestSummary);
            em.flush();

            return createInvestSummary;
        } catch (PersistenceException ex) {
            incrementPrincipal(investSettingId, year, month, principal);
            return selectInvestSummary(investSettingId, year, month);
        }
    }

    @Override
    public void deleteAllByInvestSettingId(Long investSettingId) {
        queryFactory
                .delete(investSummary)
                .where(investSummary.investSetting.id.eq(investSettingId))
                .execute();
    }

    private InvestSummary selectInvestSummary(Long investSettingId, Integer year, Integer month) {
        InvestSummary is = queryFactory
                .selectFrom(investSummary)
                .where(
                        investSummary.investSetting.id.eq(investSettingId),
                        investSummary.year.eq(year),
                        investSummary.month.eq(month)
                ).fetchOne();

        if (is != null) em.refresh(is);
        return is;
    }
}
