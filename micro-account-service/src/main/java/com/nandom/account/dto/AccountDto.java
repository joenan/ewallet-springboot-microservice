package com.nandom.account.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class AccountDto implements Serializable {
    private Long accountId;

    @NotNull(message = "surname is required")
    @ApiModelProperty(notes = "surname",name="surname",required=true,value="surname")
    private String surname;

    @NotNull(message = "first name is required")
    @ApiModelProperty(notes = "First Name",name="firstName",required=true,value="First Name")
    private String firstName;

    private String lastName;

    @NotNull(message = "Email is required")
    @ApiModelProperty(notes = "Email",name="email",required=true,value="Email")
    private String email;

    @NotNull(message = "Phone no is required")
    @ApiModelProperty(notes = "Phone",name="phone",required=true,value="Phone")
    private String phone;

    private String accountNo;

}
