package com.nandom.account.dto;

import com.nandom.account.model.Account;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

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

    private LocalDateTime date;

}
