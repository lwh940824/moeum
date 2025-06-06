package com.moeum.moeum.domain;

import com.moeum.moeum.type.CategoryType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "ledger_category_group")
public class CategoryGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long categoryGroupId;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryType categoryType;

    private String imageUrl;

    @OneToMany(mappedBy = "categoryGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> categoryList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void assignUser(User user) {
        this.user = user;
    }

    @Builder
    public CategoryGroup(Long categoryGroupId, String name, CategoryType categoryType, String imageUrl, List<Category> categoryList, User user) {
        this.name = name;
        this.categoryType = categoryType;
        this.imageUrl = imageUrl;
        this.categoryList = categoryList;
        this.user = user;
    }
}
