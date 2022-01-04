package com.nandom.transaction.dto;

import com.nandom.transaction.model.Account;
import lombok.Data;

import java.io.Serializable;

@Data
public class WalletDto implements Serializable {

    private Long walletId;

    private String walletAddress;

    private Double walletBalance;

    private Account AccountId;
}
