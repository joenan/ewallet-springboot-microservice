package com.nandom.transaction.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class FundWalletRequestDto implements Serializable {

    @NotNull(message = "amount is required")
    @ApiModelProperty(example = "amount to fund", required = true, dataType = "Double", notes = "Funding Amount")
    private Double amount;

    @NotNull(message = "wallet address is required")
    @ApiModelProperty(example = "address to fund", required = true, dataType = "String", notes = "Funding Address")
    private String walletAddress;
}
