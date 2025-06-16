package com.moeum.moeum.api.ledger.category.repository;

import com.moeum.moeum.domain.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryQueryRepository {
    List<Category> findAllByUserId(Long userId);

    Optional<Category> findByUserIdAndName(Long userId, String name);

    Optional<Category> findByUserIdAndId(Long userId, Long categoryId);
}