package com.bank.bank_sys.repository;

import com.bank.bank_sys.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}