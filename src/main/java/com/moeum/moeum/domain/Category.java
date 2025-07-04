package com.moeum.moeum.domain;

import com.moeum.moeum.type.CategoryType;
import com.moeum.moeum.type.RecurringType;
import com.moeum.moeum.type.YnType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
@Table(name = "ledger_category_group")
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryType categoryType;

    @Column(nullable = false)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YnType investmentYn;

    @Enumerated(EnumType.STRING)
    private RecurringType recurringType;

    private LocalDateTime recurringStartDt;

    private LocalDateTime recurringEndDt;

    @OneToMany(mappedBy = "category")
    private List<Item> itemList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void update(String name, CategoryType categoryType, String imageUrl) {
        this.name = name;
        this.categoryType = categoryType;
        this.imageUrl = imageUrl;
    }

    public void changeParent(Category parent) {
        this.parent = parent;
    }

    public void assignUser(User user) {
        this.user = user;
    }

    @Builder
    public Category(String name, CategoryType categoryType, String imageUrl, YnType investmentYn, RecurringType recurringType, LocalDateTime recurringStartDt, LocalDateTime recurringEndDt, List<Category> categoryList, User user) {
        this.name = name;
        this.categoryType = categoryType;
        this.imageUrl = imageUrl;
        this.investmentYn = investmentYn;
        this.recurringType = recurringType;
        this.recurringStartDt = recurringStartDt;
        this.recurringEndDt = recurringEndDt;
        this.user = user;
    }
}
