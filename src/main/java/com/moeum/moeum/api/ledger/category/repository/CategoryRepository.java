package com.moeum.moeum.api.ledger.category.repository;

import com.moeum.moeum.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByUserId(Long userId);
    List<Category> findAllByUserIdAndParentCategoryIsNotNull(Long userId);
    List<Category> findAllByUserIdAndParentCategoryIsNull(Long userId);
    Optional<Category> findByUserIdAndName(Long userId, String name);
    Optional<Category> findByUserIdAndId(Long userId, Long categoryGroupId);
}
