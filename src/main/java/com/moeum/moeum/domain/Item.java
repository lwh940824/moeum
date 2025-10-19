package com.moeum.moeum.domain;

import ch.qos.logback.core.spi.ErrorCodes;
import com.moeum.moeum.global.exception.CustomException;
import com.moeum.moeum.global.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
@Table(name = "ledger_Item")
public class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private Long amount;

    private LocalDateTime occurredAt;

    private String memo;

    private Long itemPlanId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

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

    @Builder
    public Item(Long amount, LocalDateTime occurredAt, String memo, Long itemPlanId) {
        this.amount = amount;
        this.occurredAt = occurredAt;
        this.memo = memo;
        this.itemPlanId = itemPlanId;
    }
}
