package com.moeum.moeum.api.ledger.CategoryGroup;

import com.moeum.moeum.domain.CategoryGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryGroupRepository extends JpaRepository<CategoryGroup, Long> {
    List<CategoryGroup> findAllByUserId(Long userId);

    Optional<CategoryGroup> findByUserIdAndName(Long userId, String name);

    Optional<CategoryGroup> findByCategoryGroupId(Long categoryGroupId);
}
