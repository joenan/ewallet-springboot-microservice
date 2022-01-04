package com.nandom.transaction.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccountDto implements Serializable {

    private Long accountId;

    private String surname;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String accountNo;
}
