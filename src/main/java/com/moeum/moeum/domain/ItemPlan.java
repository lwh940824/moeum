package com.moeum.moeum.domain;

import com.moeum.moeum.type.RecurringType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    private Long amount;

    private String memo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @OneToMany(mappedBy = "item")
    private List<Item> itemList = new ArrayList<>();

    public void updateItem() {

    }

    public void addItem(Item item) {
        this.itemList.add(item);
        item.assignItemPlan(this);
    }

    @Builder
    public ItemPlan(RecurringType recurringType, LocalDateTime recurringStartDate, LocalDateTime recurringEndDate) {
        this.recurringType = recurringType;
        this.recurringStartDate = recurringStartDate;
        this.recurringEndDate = recurringEndDate;
    }
}