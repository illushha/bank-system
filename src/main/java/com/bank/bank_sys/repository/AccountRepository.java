package com.bank.bank_sys.repository;

import com.bank.bank_sys.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}