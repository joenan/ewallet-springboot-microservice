package com.nandom.notification.controller;


import com.nandom.notification.dto.ApiResponseDto;
import com.nandom.notification.dto.TransactionLogRequestByDateRangeDto;
import com.nandom.notification.dto.TransactionLogRequestByWalletAddressDto;
import com.nandom.notification.dto.TransactionLogsResponseDto;
import com.nandom.notification.service.TransactionLogsService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/logs")
public class NotificationsController {

    @Autowired
    TransactionLogsService logsService;

    @PostMapping("/walletAddress")
    @ApiOperation(value = "Transaction Logs",notes = "Get Transaction Logs by wallet Address")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "Not Authorized!"),
            @ApiResponse(code = 403, message = "Forbidden!"),
            @ApiResponse(code = 404, message = "Not Found!"),
            @ApiResponse(code = 201, message = "Created", response = TransactionLogsResponseDto.class) })
    public ResponseEntity<ApiResponseDto> getTransactionLogsByWalletAddress(@Valid @RequestBody TransactionLogRequestByWalletAddressDto data) {
        return ResponseEntity.ok().body(logsService.getTransactionLogsByWalletAddress(data.getWalletAddress()));
    }

    @PostMapping("/date")
    @ApiOperation(value = "Transaction Logs By Date Range",notes = "Get Transaction Logs By Date Range")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "Not Authorized!"),
            @ApiResponse(code = 403, message = "Forbidden!"),
            @ApiResponse(code = 404, message = "Not Found!"),
            @ApiResponse(code = 201, message = "Created", response = TransactionLogsResponseDto.class) })
    public ResponseEntity<ApiResponseDto> getTransactionLogsByDateRange(@Valid @RequestBody TransactionLogRequestByDateRangeDto data) {
        return ResponseEntity.ok().body(logsService.getTransactionLogsByDateRange(data.getDateFrom(), data.getDateTo()));
    }
}
