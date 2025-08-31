package com.moeum.moeum.api.ledger.investSetting.repository;

import com.moeum.moeum.domain.InvestSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InvestSettingRepository extends JpaRepository<InvestSetting, Long> {

    @Query("""
        select s 
        from InvestSetting s
        where s.category.user.id = :userId
        and s.id = :id
    """)
    Optional<InvestSetting> findById(Long userId, Long id);

    @Query("""
        select s 
        from InvestSetting s 
        where s.category.user.id = :userId
    """)
    List<InvestSetting> findAllByUserId(Long userId);

    @Query("""
        select s 
        from InvestSetting s 
        where s.category.user.id = :userId 
        and s.category.id = :categoryId
    """)
    Optional<InvestSetting> findByUserIdAndCategoryId(Long userId, Long categoryId);
}
