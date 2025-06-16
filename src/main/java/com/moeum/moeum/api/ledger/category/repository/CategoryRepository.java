package com.moeum.moeum.api.ledger.category.repository;

import com.moeum.moeum.api.ledger.categoryGroup.repository.ItemQueryRepository;
import com.moeum.moeum.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryQueryRepository {

}
