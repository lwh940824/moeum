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

    @OneToMany(mappedBy = "itemPlan")
    private List<Item> itemList = new ArrayList<>();

    public void reflectItem(Item item) {
        this.amount = item.getAmount();
        this.memo = item.getMemo();
        this.category = item.getCategory();
        this.payment = item.getPayment();
    }

    public void addItem(Item item) {
        this.itemList.add(item);
        item.assignItemPlan(this);
    }

    public void removeItem(Item item) {
        this.itemList.remove(item);
    }

    @Builder
    public ItemPlan(RecurringType recurringType, LocalDateTime recurringStartDate, LocalDateTime recurringEndDate, Long amount, String memo, Category category, Payment payment) {
        this.recurringType = recurringType;
        this.recurringStartDate = recurringStartDate;
        this.recurringEndDate = recurringEndDate;
        this.amount = amount;
        this.memo = memo;
        this.category = category;
        this.payment = payment;
    }
}