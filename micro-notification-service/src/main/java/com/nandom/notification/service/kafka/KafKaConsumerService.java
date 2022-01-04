package com.nandom.notification.service.kafka;

import com.nandom.notification.constants.AppConstants;
import com.nandom.notification.dto.WalletResponseDto;
import com.nandom.notification.model.TransactionLogs;
import com.nandom.notification.service.EmailService;
import com.nandom.notification.service.TransactionLogsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class KafKaConsumerService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private TransactionLogsService logsService;


    private final Logger logger = LoggerFactory.getLogger(KafKaConsumerService.class);

    @KafkaListener(topics = AppConstants.FUNDING_TOPIC_NAME, groupId = AppConstants.FUNDING_GROUP_ID)
    public void consumeFundSuccessDetails(WalletResponseDto message) {
        logger.info(String.format("Funding Transaction Message recieved -> %s", message));

        //save the transaction Log to the database
        saveDepositTransactionLogsToDatabase(message);
        sendFundingNotificationMessage(message);

    }

    @KafkaListener(topics = AppConstants.WITHDRAWAL_TOPIC_NAME, groupId = AppConstants.WITHDRAWAL_GROUP_ID)
    public void consumeWithdrawalSuccessDetails(WalletResponseDto message) {
        logger.info(String.format("Withdrawal Transaction Message recieved -> %s", message));

        saveWithdrawalTransactionLogsToDatabase(message);
        sendWithdrawalNotificationMessage(message);

    }

    @KafkaListener(topics = AppConstants.TRANSFER_TOPIC_NAME, groupId = AppConstants.TRANSFER_GROUP_ID)
    public void consumeTransferSuccessDetails(WalletResponseDto message) {
        logger.info(String.format("Transfer Transaction Message recieved -> %s", message));

        saveTransferTransactionLogsToDatabase(message);
        sendTransferNotificationMessage(message);

    }

    @KafkaListener(topics = AppConstants.ACCOUNT_TOPIC_NAME, groupId = AppConstants.ACCOUNT_GROUP_ID)
    public void consumeAccountNotifications(WalletResponseDto message)
    {
        sendAccountSuccessMessage(message);
    }


    public void sendAccountSuccessMessage(WalletResponseDto message) {
        System.out.println("Account Message recieved" + message);
        String recipient = message.getEmail();
        String subject = "NEW WALLET ACCOUNT";
        String messageBody = "Dear " + message.getFirstName() + "\n"
                + "Your wallet account has been successfully created. Please find below the account details" + "\n \n"
                + "Date Created: " + message.getDate() + "\n"
                + "Wallet Address: " + message.getWalletAddress() + "\n"
                + "Wallet Balance: " + message.getWalletBalance() + "\n"
                + "surname: " + message.getSurname() + "\n"
                + "firstName: " + message.getFirstName() + "\n"
                + "lastName: " + message.getLastName() + "\n"
                + "email: " + message.getEmail() + "\n"
                + "phone: " + message.getPhone() + "\n"
                + "accountNo: " +message.getAccountNo() + "\n";

        emailService.sendMail(recipient, subject, messageBody);
    }

    public void sendFundingNotificationMessage(WalletResponseDto message) {
        System.out.println("Funding Account Message recieved" + message);
        String recipient = message.getEmail();
        String subject = "ACCOUNT DEPOSIT NOTIFICATION";
        String messageBody = "Dear " + message.getFirstName() + "\n"
                + "Your wallet account has been successfully funded. Please find the details below" + "\n \n"
                + "Transaction Date: " + message.getDate() + "\n"
                + "Wallet Address" + message.getWalletAddress() + "\n"
                + "Amount Funded" + message.getAmount() + "\n"
                + "Wallet Balance" + message.getWalletBalance() + "\n"
                + "surname" + message.getSurname() + "\n"
                + "First Name" + message.getFirstName() + "\n"
                + "Last Name" + message.getLastName() + "\n"
                + "email" + message.getEmail() + "\n"
                + "phone" + message.getPhone() + "\n"
                + "Account No" +message.getAccountNo() + "\n";

        emailService.sendMail(recipient, subject, messageBody);
    }

    public void sendWithdrawalNotificationMessage(WalletResponseDto message) {
        System.out.println("Account Withdrawal Message recieved" + message);
        String recipient = message.getEmail();
        String subject = "ACCOUNT WITHDRAWAL NOTIFICATION";
        String messageBody = "Dear " + message.getFirstName() + "\n"
                + "Your wallet account has been debited. Please find the details below" + "\n \n"
                + "Wallet Address" + message.getWalletAddress() + "\n"
                + "Amount Withdrawn" + message.getAmount() + "\n"
                + "Wallet Balance" + message.getWalletBalance() + "\n"
                + "surname" + message.getSurname() + "\n"
                + "First Name" + message.getFirstName() + "\n"
                + "Last Name" + message.getLastName() + "\n"
                + "email" + message.getEmail() + "\n"
                + "phone" + message.getPhone() + "\n"
                + "Account No" +message.getAccountNo() + "\n";

        emailService.sendMail(recipient, subject, messageBody);
    }

    public void sendTransferNotificationMessage(WalletResponseDto message) {
        System.out.println("Account Message recieved" + message);
        String recipient = message.getEmail();
        String subject = "TRANSFER REQUEST NOTIFICATION";
        String messageBody = "Dear " + message.getFirstName() + "\n"
                + "Your wallet transfer request is successful. Please find the details below" + "\n \n"
                + "Wallet Address" + message.getWalletAddress() + "\n"
                + "Amount Transferred" + message.getAmount() + "\n"
                + "Wallet Balance" + message.getWalletBalance() + "\n"
                + "surname" + message.getSurname() + "\n"
                + "First Name" + message.getFirstName() + "\n"
                + "Last Name" + message.getLastName() + "\n"
                + "email" + message.getEmail() + "\n"
                + "phone" + message.getPhone() + "\n"
                + "Account No" +message.getAccountNo() + "\n";

        emailService.sendMail(recipient, subject, messageBody);
    }


    void saveDepositTransactionLogsToDatabase(WalletResponseDto data) {
        TransactionLogs logs = new TransactionLogs();
        logs.setDate(data.getDate());
        logs.setAccountNo(data.getAccountNo());
        logs.setAmount(data.getAmount());
        logs.setTransactionType("DEPOSIT");
        logs.setWalletAddress(data.getWalletAddress());

        logsService.saveTransactionLogs(logs);
    }

    void saveWithdrawalTransactionLogsToDatabase(WalletResponseDto data) {
        TransactionLogs logs = new TransactionLogs();
        logs.setDate(data.getDate());
        logs.setAccountNo(data.getAccountNo());
        logs.setAmount(data.getAmount());
        logs.setTransactionType("WITHDRAWAL");
        logs.setWalletAddress(data.getWalletAddress());

        logsService.saveTransactionLogs(logs);
    }

    void saveTransferTransactionLogsToDatabase(WalletResponseDto data) {
        TransactionLogs logs = new TransactionLogs();
        logs.setDate(data.getDate());
        logs.setAccountNo(data.getAccountNo());
        logs.setAmount(data.getAmount());
        logs.setTransactionType("TRANSFER");
        logs.setWalletAddress(data.getWalletAddress());

        logsService.saveTransactionLogs(logs);
    }

}