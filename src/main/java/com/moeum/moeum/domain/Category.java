package com.moeum.moeum.domain;

import com.moeum.moeum.type.RecurringType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "ledger_category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer categoryId;

    private String name;

    @Enumerated(EnumType.STRING)
    private RecurringType recurringType;

    private LocalDateTime recurring_start_dt;

    private LocalDateTime recurring_end_dt;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_group_id", nullable = false)
    private CategoryGroup categoryGroup;

    @Builder
    public Category(Integer categoryId, String name, RecurringType recurringType, LocalDateTime recurring_start_dt, LocalDateTime recurring_end_dt, String imageUrl, CategoryGroup categoryGroup) {
        this.name = name;
        this.recurringType = recurringType;
        this.recurring_start_dt = recurring_start_dt;
        this.recurring_end_dt = recurring_end_dt;
        this.imageUrl = imageUrl;
        this.categoryGroup = categoryGroup;
    }
}

