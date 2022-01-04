package com.nandom.transaction.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class WalletTransferRequestDto implements Serializable {

    @NotNull(message = "amount is required")
    @ApiModelProperty(example = "amount to fund", required = true, dataType = "Double", notes = "Funding Amount")
    private Double amount;

    @NotNull(message = "Debit wallet address is required")
    @ApiModelProperty(example = "wallet address to debit", required = true, dataType = "String", notes = "Debit Wallet Address")
    private String sourceWalletAddress;

    @NotNull(message = "Credit wallet address is required")
    @ApiModelProperty(example = "wallet address to credit", required = true, dataType = "String", notes = "Credit Wallet Address")
    private String destinationWalletAddress;




}
