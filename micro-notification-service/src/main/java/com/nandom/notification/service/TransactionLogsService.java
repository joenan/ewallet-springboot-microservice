package com.nandom.notification.service;

import com.nandom.notification.dto.ApiResponseDto;
import com.nandom.notification.model.TransactionLogs;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface TransactionLogsService {
  ApiResponseDto saveTransactionLogs(TransactionLogs data);
  ApiResponseDto getTransactionLogsByWalletAddress(String walletAddress);
  ApiResponseDto getTransactionLogsByDateRange(LocalDateTime dateFrom, LocalDateTime dateTo);
}
