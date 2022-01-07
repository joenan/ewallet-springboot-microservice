package com.nandom.account;


import com.nandom.account.repository.WalletRepository;
import com.nandom.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {WalletRepository.class, AccountService.class})
public class AccountTest {

    @Autowired
    private AccountService service;
}
