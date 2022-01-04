package com.nandom.transaction.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
public class WithdrawWalletRequestDto implements Serializable {

    @NotNull(message = "amount is required")
    @ApiModelProperty(example = "amount to transfer", required = true, dataType = "Double", notes = "Transfer Amount")
    private Double amount;

    @NotNull(message = "wallet address is required")
    @ApiModelProperty(example = "wallet addresss to withdraw", required = true, dataType = "String", notes = "Withdrawing Address")
    private String walletAddress;

}
