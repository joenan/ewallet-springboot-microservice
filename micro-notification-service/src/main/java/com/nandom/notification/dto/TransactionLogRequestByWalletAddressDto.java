package com.nandom.notification.dto;


import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class TransactionLogRequestByWalletAddressDto implements Serializable {

    @NotNull(message = "Wallet Address must not be empty")
    private String walletAddress;
}
