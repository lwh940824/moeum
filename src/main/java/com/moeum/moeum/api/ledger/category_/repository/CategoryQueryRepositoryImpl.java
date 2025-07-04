//package com.moeum.moeum.api.ledger.category_.repository;
//
//import com.moeum.moeum.domain.Category;
//import com.moeum.moeum.domain.QCategory;
//import com.moeum.moeum.domain.QCategoryGroup;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//@RequiredArgsConstructor
//public class CategoryQueryRepositoryImpl implements CategoryQueryRepository {
//
//    private final JPAQueryFactory queryFactory;
//
//    @Override
//    public List<Category> findAllByUserId(Long userId) {
//        QCategory category = QCategory.category;
//        QCategoryGroup categoryGroup = QCategoryGroup.categoryGroup;
//
//        return queryFactory
//                .selectFrom(category)
//                .join(category.categoryGroup, categoryGroup).fetchJoin()
//                .where(
//                        categoryGroup.user.id.eq(userId)
//                ).fetch();
//    }
//
//    @Override
//    public Optional<Category> findByUserIdAndName(Long userId, String name) {
//        QCategory category = QCategory.category;
//        QCategoryGroup categoryGroup = QCategoryGroup.categoryGroup;
//
//        return Optional.ofNullable(queryFactory
//                .selectFrom(category)
//                .join(category.categoryGroup, categoryGroup).fetchJoin()
//                .where(
//                        categoryGroup.user.id.eq(userId)
//                                .and(category.name.eq(name))
//                ).fetchOne());
//    }
//
//    @Override
//    public Optional<Category> findByUserIdAndId(Long userId, Long categoryId) {
//        QCategory category = QCategory.category;
//        QCategoryGroup categoryGroup = QCategoryGroup.categoryGroup;
//
//        return Optional.ofNullable(queryFactory
//                .selectFrom(category)
//                .join(category.categoryGroup, categoryGroup).fetchJoin()
//                .where(
//                        categoryGroup.user.id.eq(userId)
//                                .and(category.id.eq(categoryId))
//                ).fetchOne()
//        );
//    }
//}
