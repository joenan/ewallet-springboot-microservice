package com.nandom.transaction.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ApiResponseDto {
    private int responseCode;
    private String responseMessage;
    private Object data;
}