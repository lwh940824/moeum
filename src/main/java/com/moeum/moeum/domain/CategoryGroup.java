package com.moeum.moeum.domain;

import com.moeum.moeum.type.CategoryType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "ledger_category_group")
public class CategoryGroup extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "category_group_id")
    private Long id;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryType categoryType;

    @Column(nullable = false)
    private String imageUrl;

    @OneToMany(mappedBy = "categoryGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> categoryList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void update(String name, CategoryType categoryType, String imageUrl) {
        this.name = name;
        this.categoryType = categoryType;
        this.imageUrl = imageUrl;
    }

    public void assignUser(User user) {
        this.user = user;
    }

//    @Builder
//    public CategoryGroup(String name, CategoryType categoryType, String imageUrl, List<Category> categoryList, User user) {
//        this.name = name;
//        this.categoryType = categoryType;
//        this.imageUrl = imageUrl;
//        this.categoryList = categoryList;
//        this.user = user;
//    }
}
