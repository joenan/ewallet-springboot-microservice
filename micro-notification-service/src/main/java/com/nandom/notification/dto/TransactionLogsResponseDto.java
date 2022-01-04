package com.nandom.notification.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class TransactionLogsResponseDto implements Serializable {

    private Long logId;

    private String transactionType;

    private String walletAddress;

    private String accountNo;

    private Double amount;

    private LocalDateTime date;

    private String type;

}

