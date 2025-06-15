package com.moeum.moeum.domain;

import ch.qos.logback.core.spi.ErrorCodes;
import com.moeum.moeum.global.exception.CustomException;
import com.moeum.moeum.global.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "ledger_Item")
public class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long itemId;

    private Long amount;

    private LocalDateTime occurred_at;

    private String memo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    public void update(Long amount, LocalDateTime occurred_at, String memo) {
        this.amount = amount;
        this.occurred_at = occurred_at;
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

//    @Builder
//    public Item(Long itemId, Long amount, LocalDateTime occurred_at, Category category, Payment payment) {
//        this.amount = amount;
//        this.occurred_at = occurred_at;
//        this.category = category;
//        this.payment = payment;
//    }
}
