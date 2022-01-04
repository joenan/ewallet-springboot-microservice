package com.nandom.notification.repository;

import com.nandom.notification.model.TransactionLogs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionLogsRepository extends JpaRepository<TransactionLogs, Long> {
}
