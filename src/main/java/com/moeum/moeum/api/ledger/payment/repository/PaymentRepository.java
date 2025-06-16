package com.moeum.moeum.api.ledger.payment.repository;

import com.moeum.moeum.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllByUserId(Long userId);

    Optional<Payment> findByUserIdAndName(Long userId, String name);

    Optional<Payment> findByUserIdAndId(Long userId, Long paymentId);
}
