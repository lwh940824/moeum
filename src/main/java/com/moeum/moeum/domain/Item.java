package com.moeum.moeum.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "ledger_Item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long itemId;

    private Long amount;

    private LocalDateTime occurred_at;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @Builder
    public Item(Long itemId, Long amount, LocalDateTime occurred_at, Category category, Payment payment) {
        this.amount = amount;
        this.occurred_at = occurred_at;
        this.category = category;
        this.payment = payment;
    }
}
