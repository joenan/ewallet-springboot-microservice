package com.nandom.notification.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class WalletResponseDto implements Serializable {

    private Long walletId;

    private String walletAddress;

    private Double walletBalance;

    private Long accountId;

    private String surname;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String accountNo;

    private Double amount;

    private LocalDateTime date;

}
