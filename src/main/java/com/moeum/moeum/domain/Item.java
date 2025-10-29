package com.moeum.moeum.domain;

import ch.qos.logback.core.spi.ErrorCodes;
import com.moeum.moeum.global.exception.CustomException;
import com.moeum.moeum.global.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
@Table(name = "ledger_item")
public class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private Long amount;

    private LocalDateTime occurredAt;

    private String memo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_plan_id")
    private ItemPlan itemPlan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void update(Long amount, LocalDateTime occurredAt, String memo) {
        this.amount = amount;
        this.occurredAt = occurredAt;
        this.memo = memo;
    }

    public void changeCategory(Category category) {
        if (category == null) throw new CustomException(ErrorCode.REQUIRED_CATEGORY);
        if (this.category != null) this.category.getItemList().remove(this);

        if (!category.getItemList().contains(this)) {
            category.getItemList().add(this);
        }

        this.category = category;
    }

    public void changePayment(Payment payment) {
        if (payment == null) throw new CustomException(ErrorCode.REQUIRED_PAYMENT);
        this.payment = payment;
    }

    public void assignItemPlan(ItemPlan itemPlan) {
        this.itemPlan = itemPlan;
    }

    public void assignUser(User user) {
        this.user = user;
    }

    @Builder
    public Item(Long amount, LocalDateTime occurredAt, String memo) {
        this.amount = amount;
        this.occurredAt = occurredAt;
        this.memo = memo;
    }
}
