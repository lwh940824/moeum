package com.moeum.moeum.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
@Table(name = "legder_invest_summary")
public class InvestSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private Long year;

    @Column(nullable = false)
    private Long month;

    @Column(nullable = false)
    private Long principal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invest_setting_id")
    private InvestSetting investSetting;

    @Builder
    public InvestSummary(Long year, Long month, Long principal) {
        this.year = year;
        this.month = month;
        this.principal = principal;
    }

    public void changeInvestSetting(InvestSetting investSetting) {
        if (investSetting == null) return;
        this.investSetting = investSetting;
    }

    public void addPrincipal(Long principal) {
        this.principal += principal;
    }
}
