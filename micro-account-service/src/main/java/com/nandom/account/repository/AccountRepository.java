package com.nandom.account.repository;

import com.nandom.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT c from Account c where c.email=?1 OR c.phone=?2")
    Optional<Account> findByEmailOrPhone(String email, String phone);
}
