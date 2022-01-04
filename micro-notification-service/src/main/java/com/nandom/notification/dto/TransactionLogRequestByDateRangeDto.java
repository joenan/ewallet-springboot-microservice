package com.nandom.notification.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TransactionLogRequestByDateRangeDto implements Serializable {

    @NotNull(message = "Date From cannot be null or empty")
    LocalDateTime dateFrom;

    @NotNull(message = "Date To cannot be null or empty")
    LocalDateTime dateTo;
}
