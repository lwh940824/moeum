package com.moeum.moeum.domain;

import com.moeum.moeum.type.RecurringType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
@Table(name = "ledger_item_plan")
public class ItemPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private RecurringType recurringType;

    @Column(nullable = false)
    private LocalDateTime recurringStartDate;

    @Column(nullable = false)
    private LocalDateTime recurringEndDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    void changeItem(Item item) {
        this.item = item;
    }

    @Builder
    public ItemPlan(RecurringType recurringType, LocalDateTime recurringStartDate, LocalDateTime recurringEndDate) {
        this.recurringType = recurringType;
        this.recurringStartDate = recurringStartDate;
        this.recurringEndDate = recurringEndDate;
    }
}