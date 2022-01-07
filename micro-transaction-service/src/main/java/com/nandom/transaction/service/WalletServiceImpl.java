package com.nandom.transaction.service;

import com.nandom.transaction.dto.ApiResponseDto;
import com.nandom.transaction.dto.WalletResponseDto;
import com.nandom.transaction.model.Wallet;
import com.nandom.transaction.repository.WalletRepository;
import com.nandom.transaction.service.kafka.KafKaProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class WalletServiceImpl implements WalletService {

    @Autowired
    WalletRepository walletRepository;

    private final KafKaProducerService producerService;

    private static final Logger logger = LoggerFactory.getLogger(WalletServiceImpl.class);

    @Autowired
    public WalletServiceImpl(KafKaProducerService producerService) {
        this.producerService = producerService;
    }

    @Override
    public ApiResponseDto fundWallet(double amount, String walletAddress) {
        Optional<Wallet> wallet = findByWalletAddress(walletAddress);
        ApiResponseDto response = new ApiResponseDto();


        if (wallet.isPresent()) {
            Wallet saveFund = addFundsToWallet(amount, walletAddress, wallet.get());
            response.setData(saveFund);
            response.setResponseCode(HttpStatus.OK.value());
            response.setResponseMessage("Funding was successful");

            //sending fund success information to Kafka for email notification
            sendFundingSuccessDetailsToKafkaTopic(maptoWalletResponseDto(saveFund, amount));
            return response;
        }
        response.setResponseMessage("Wallet Address does not exist");
        response.setResponseCode(HttpStatus.NOT_FOUND.value());

        return response;
    }

    @Override
    public ApiResponseDto withdrawFromWallet(double amount, String walletAddress) {
        Optional<Wallet> wallet = findByWalletAddress(walletAddress);
        ApiResponseDto response = new ApiResponseDto();


        //Check if account have funds in it
        if (wallet.isPresent()) {
            if (amount > wallet.get().getWalletBalance()) {
                response.setResponseCode(HttpStatus.CONFLICT.value());
                response.setResponseMessage("Insufficient Funds");
                return response;
            }
        }

        if (wallet.isPresent()) {
            Wallet withdrawFund = removeFundsFromWallet(amount, walletAddress, wallet.get());
            response.setData(withdrawFund);
            response.setResponseCode(HttpStatus.OK.value());
            response.setResponseMessage("Withdrawal was successful");

            //sending withdrawal success information to Kafka for email notification
            sendWithdrawalSuccessDetailsToKafkaTopic(maptoWalletResponseDto(withdrawFund, amount));
            return response;
        }
        response.setResponseMessage("Wallet Address does not exist");
        response.setResponseCode(HttpStatus.NOT_FOUND.value());
        return response;
    }

    @Override
    public ApiResponseDto transferToWallet(double amount, String sourceWalletAddress, String destinationWalletAddress) {

        Optional<Wallet> sourceWallet = findByWalletAddress(sourceWalletAddress);
        Optional<Wallet> destinationWallet = findByWalletAddress(destinationWalletAddress);

        ApiResponseDto response = new ApiResponseDto();

        //Check if there is sufficient funds in the account
        if (sourceWallet.isPresent()) {
            if (amount > sourceWallet.get().getWalletBalance()) {
                response.setResponseCode(HttpStatus.CONFLICT.value());
                response.setResponseMessage("Insufficient Funds");
                return response;
            }
        }


        if (sourceWallet.isPresent()) {
            if (destinationWallet.isPresent()) {
                Wallet withdrawFromSourceWallet = removeFundsFromWallet(amount, sourceWalletAddress, sourceWallet.get());
                Wallet creditDestinationWallet = addFundsToWallet(amount, sourceWalletAddress, sourceWallet.get());

                //sending Transfer success information to Kafka for email notification
                sendWithdrawalSuccessDetailsToKafkaTopic(maptoWalletResponseDto(withdrawFromSourceWallet, amount));
                response.setResponseMessage("Fund transfer was successful");
                response.setResponseCode(HttpStatus.OK.value());
                return response;
            } else {
                response.setResponseCode(HttpStatus.NOT_FOUND.value());
                response.setResponseMessage("Credit wallet address is not found");
                return response;
            }

        } else {
            response.setResponseCode(HttpStatus.NOT_FOUND.value());
            response.setResponseMessage("Debit wallet address is not found");
            return response;
        }
    }


    public Optional<Wallet> findByWalletAddress(String walletAddress) {
        return walletRepository.findByWalletAddress(walletAddress);
    }


    public Wallet addFundsToWallet(double amount, String walletAddress, Wallet wallet) {
        double currentAmount = wallet.getWalletBalance();
        double newAmount = currentAmount + amount;
        wallet.setWalletBalance(newAmount);
        return walletRepository.save(wallet);
    }

    public Double checkWalletBalance(String walletAddress) {
        Optional<Wallet> wallet = findByWalletAddress(walletAddress);
        double currentBalance = wallet.get().getWalletBalance();
        return currentBalance;
    }

    public Wallet removeFundsFromWallet(double amount, String walletAddress, Wallet wallet) {
        double currentAmount = wallet.getWalletBalance();
        double newAmount = currentAmount - amount;
        wallet.setWalletBalance(newAmount);
        return walletRepository.save(wallet);
    }

    public void sendFundingSuccessDetailsToKafkaTopic(WalletResponseDto message) {
        this.producerService.sendFundSuccessDetails(message);
    }

    public void sendWithdrawalSuccessDetailsToKafkaTopic(WalletResponseDto message) {
        this.producerService.sendWithdrawalSuccessDetails(message);
    }

    public void sendTransferSuccessDetailsToKafkaTopic(WalletResponseDto message) {
        this.producerService.sendTransferSuccessDetails(message);
    }

    public WalletResponseDto maptoWalletResponseDto(Wallet data, double amount) {
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
        dto.setAmount(amount);
        dto.setDate(LocalDateTime.now());
        return dto;
    }

}
