package com.nandom.transaction.repository;

import com.nandom.transaction.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByWalletAddress(String walletAddress);

}
