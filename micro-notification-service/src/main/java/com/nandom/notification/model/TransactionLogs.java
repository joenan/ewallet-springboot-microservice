package com.nandom.notification.model;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name="transaction_logs")
@Data
public class TransactionLogs implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long logId;

    @Column(name = "wallet_address")
    private String walletAddress;

    @Column(name = "account_no")
    private String accountNo;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "transaction_type")
    private String transactionType;

}
