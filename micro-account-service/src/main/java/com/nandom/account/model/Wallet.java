package com.nandom.account.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


@Data
@Entity
@Table(name="wallet")
public class Wallet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long walletId;

    @Column(name = "wallet_address")
    private String walletAddress;

    @Column(name = "wallet_balance")
    private Double walletBalance;

    @Column(name="date_created")
    private LocalDateTime dateCreated;

    @JoinColumn(name = "account_id", referencedColumnName = "account_id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Account account;

}
