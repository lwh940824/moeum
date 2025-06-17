package com.moeum.moeum.domain;

import com.moeum.moeum.type.InterestType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
@Table(name = "ledger_asset_plan")
public class AssetPlan extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer assetPlanId;

    private Integer interest_rate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InterestType interestType;

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Builder
    public AssetPlan(Integer assetPlanId, Integer interest_rate, InterestType interestType, Category category) {
        this.interest_rate = interest_rate;
        this.interestType = interestType;
        this.category = category;
    }
}