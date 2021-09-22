package com.kimkb1104.auth.account.repository;

import com.kimkb1104.auth.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);

    Optional<Account> findByRefreshToken(String refreshToken);
}
