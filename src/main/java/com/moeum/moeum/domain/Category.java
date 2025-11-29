package com.moeum.moeum.domain;

import com.moeum.moeum.type.CategoryType;
import com.moeum.moeum.type.YnType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
@Table(name = "ledger_category")
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
    private YnType useYn;

    @OneToMany(mappedBy = "category")
    private List<Item> itemList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;

    @OneToMany(mappedBy = "childCategory")
    private List<Category> childCategoryList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void update(String name, CategoryType categoryType, String imageUrl) {
        this.name = name;
        this.categoryType = categoryType;
        this.imageUrl = imageUrl;
    }

    public void changeParentCategory(Category parentCategory) {
        if (this.parentCategory != null) {
            this.parentCategory.childCategoryList.remove(this);
        }

        this.parentCategory = parentCategory;

        if (parentCategory != null) {
            parentCategory.childCategoryList.add(this);
        }
    }

    public void deactivate(YnType useYn) {
        this.useYn = useYn;
    }

    public void assignUser(User user) {
        this.user = user;
    }

    @Builder
    public Category(String name, CategoryType categoryType, String imageUrl, YnType investmentYn, YnType useYn, User user) {
        this.name = name;
        this.categoryType = categoryType;
        this.imageUrl = imageUrl;
        this.investmentYn = investmentYn;
        this.useYn = useYn;
        this.user = user;
    }
}