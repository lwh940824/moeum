package com.moeum.moeum.api.ledger.CategoryGroup;

import com.moeum.moeum.domain.CategoryGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryGroupRepository extends JpaRepository<CategoryGroup, Long> {
    List<CategoryGroup> findAllByUserId(Long userId);
}
