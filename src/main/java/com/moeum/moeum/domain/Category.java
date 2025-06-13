package com.moeum.moeum.domain;

import com.moeum.moeum.global.exception.CustomException;
import com.moeum.moeum.global.exception.ErrorCode;
import com.moeum.moeum.type.RecurringType;
import com.moeum.moeum.type.YnType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "ledger_category")
public class Category extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "categoryId")
    private Integer id;

    @Column(nullable = false, length = 10)
    private String name;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YnType investmentYn;

    @Enumerated(EnumType.STRING)
    private RecurringType recurringType;

    private LocalDateTime recurringStartDt;

    private LocalDateTime recurringEndDt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_group_id", nullable = false)
    private CategoryGroup categoryGroup;

    public void changeCategoryGroup(CategoryGroup categoryGroup) {
        if (categoryGroup == null) throw new CustomException(ErrorCode.REQUIRED_CATEGORY_GROUP);
        if (this.categoryGroup != null) this.categoryGroup.getCategoryList().remove(this);

        this.categoryGroup = categoryGroup;
    }

    @Builder
    public Category(String name, String imageUrl, YnType investmentYn, RecurringType recurringType, LocalDateTime recurringStartDt, LocalDateTime recurringEndDt, CategoryGroup categoryGroup) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.investmentYn = investmentYn;
        this.recurringType = recurringType;
        this.recurringStartDt = recurringStartDt;
        this.recurringEndDt = recurringEndDt;
        this.categoryGroup = categoryGroup;
    }
}

