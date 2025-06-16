package com.moeum.moeum.api.ledger.user.repository;

import com.moeum.moeum.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailAndProvider(String email, String provider);

    Optional<User> findByEmail(String email);
}
