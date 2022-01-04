package com.nandom.notification.repository;

import com.nandom.notification.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByWalletAddress(String walletAddress);
    Optional<List<Wallet>> findAllByWalletAddress(String walletAddress);

    Optional<List<Wallet>> findByDateCreatedBetween(LocalDateTime dateFrom, LocalDateTime dateTo);
}
