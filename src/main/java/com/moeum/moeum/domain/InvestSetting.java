package com.moeum.moeum.domain;

import com.moeum.moeum.type.YnType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
@Table(name = "ledger_invest_setting")
public class InvestSetting extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YnType showYn = YnType.Y;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YnType useYn = YnType.Y;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false, unique = true)
    private Category category;

    public void changeCategory(Category category) {
        this.category = category;
    }

    public void changeShowYn(YnType showYn) {
        if (this.showYn == showYn) return;
        this.showYn = showYn;
    }

    public void changeUseYn(YnType useYn) {
        if (this.useYn == useYn) return;
        this.useYn = useYn;
    }

    @Builder
    public InvestSetting(YnType useYn, YnType showYn) {
        this.useYn = (useYn != null) ? useYn : YnType.Y;
        this.showYn = (showYn != null) ? showYn : YnType.Y;
    }
}
