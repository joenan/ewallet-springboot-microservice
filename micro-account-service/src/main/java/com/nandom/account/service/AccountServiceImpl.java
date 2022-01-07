package com.nandom.account.service;

import com.nandom.account.dto.WalletResponseDto;
import com.nandom.account.model.Account;
import com.nandom.account.model.Wallet;
import com.nandom.account.dto.AccountRequestDto;
import com.nandom.account.dto.ApiResponseDto;
import com.nandom.account.repository.AccountRepository;
import com.nandom.account.repository.WalletRepository;
import com.nandom.account.service.kafka.KafKaProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@Transactional
public class AccountServiceImpl implements AccountService{

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    WalletRepository walletRepository;

    private final KafKaProducerService producerService;

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    public AccountServiceImpl(KafKaProducerService producerService)
    {
        this.producerService = producerService;
    }

    @Override
    public ApiResponseDto createAccount(AccountRequestDto dto) {

        ApiResponseDto response = new ApiResponseDto();
        //Check if account already exist

        Optional<Account> accountQuery = accountRepository.findByEmailOrPhone(dto.getEmail(), dto.getPhone());
        if(accountQuery.isPresent()) {

            response.setResponseCode(HttpStatus.CONFLICT.value());
            response.setResponseMessage("Record with this phone or email Already exist");
            return response;
        }

        Account account = new Account();
        account.setAccountId(dto.getAccountId());
        account.setAccountNo(generateAccountNo());
        account.setEmail(dto.getEmail());
        account.setFirstName(dto.getFirstName());
        account.setLastName(dto.getLastName());
        account.setPhone(dto.getPhone());
        account.setSurname(dto.getSurname());

        Account persistedAccount = accountRepository.save(account);

        Wallet wallet = new Wallet();
        wallet.setDateCreated(LocalDateTime.now());
        wallet.setAccount(persistedAccount);
        wallet.setWalletAddress(generateString());
        wallet.setWalletBalance(0.00);

        Wallet resp = walletRepository.save(wallet);

        response.setData(maptoWalletResponseDto(resp));
        response.setResponseCode(HttpStatus.CREATED.value());
        response.setResponseMessage("Account creation was successful");

        //sending created account to Kafka
        System.out.println("Sending Object From Producer to Kafka Consumer" + resp);
        sendCreatedAccountDetailsToKafkaTopic(maptoWalletResponseDto(resp));

       return response;
    }

    @Override
    public ApiResponseDto updateAccount(AccountRequestDto dto) {

        ApiResponseDto response = new ApiResponseDto();

        Account account = new Account();
        account.setAccountId(dto.getAccountId());
        account.setAccountNo(dto.getAccountNo());
        account.setEmail(dto.getEmail());
        account.setFirstName(dto.getFirstName());
        account.setLastName(dto.getLastName());
        account.setPhone(dto.getPhone());
        account.setSurname(dto.getSurname());

        Account resp = accountRepository.save(account);

        response.setData(resp);
        response.setResponseCode(HttpStatus.OK.value());
        response.setResponseMessage("Account update was successful");

//        sendCreatedAccountDetailsToKafkaTopic(resp);


        return response;
    }


    public static String generateString() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }
    public static String generateAccountNo() {
        Random rand = new Random();
        int num = rand.nextInt(9000000) + 1000000;
        return String.valueOf(num);
    }

    public void sendCreatedAccountDetailsToKafkaTopic(WalletResponseDto message)
    {
        this.producerService.sendMessage(message);
    }


    public WalletResponseDto maptoWalletResponseDto(Wallet data) {
        WalletResponseDto dto = new WalletResponseDto();
        dto.setAccountId(data.getAccount().getAccountId());
        dto.setAccountNo(data.getAccount().getAccountNo());
        dto.setEmail(data.getAccount().getEmail());
        dto.setFirstName(data.getAccount().getFirstName());
        dto.setLastName(data.getAccount().getLastName());
        dto.setPhone(data.getAccount().getPhone());
        dto.setSurname(data.getAccount().getSurname());
        dto.setWalletAddress(data.getWalletAddress());
        dto.setWalletBalance(data.getWalletBalance());
        dto.setWalletId(data.getWalletId());
        return dto;
    }

}
