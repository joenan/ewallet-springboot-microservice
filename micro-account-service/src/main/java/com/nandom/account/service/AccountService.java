package com.nandom.account.service;

import com.nandom.account.dto.AccountRequestDto;
import com.nandom.account.dto.ApiResponseDto;

public interface AccountService {
    ApiResponseDto createAccount(AccountRequestDto dto);
    ApiResponseDto updateAccount(AccountRequestDto dto);
}
