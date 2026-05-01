package com.bank.bank_sys.repository;

import com.bank.bank_sys.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
