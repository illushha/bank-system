package com.bank.bank_sys.service;

import com.bank.bank_sys.entity.Account;
import com.bank.bank_sys.repository.AccountRepository;
import com.bank.bank_sys.util.LuhnUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    public void save(Account account) {
        if (account.getNumber() == null || account.getNumber().isEmpty()) {
            account.setNumber(generateCardNumber());
        }
        accountRepository.save(account);
    }
    public String generateCardNumber() {
        String base = "400000" + (int)(Math.random() * 100000000); // 15 цифр
        int checkDigit = LuhnUtil.generateCheckDigit(base);
        return base + checkDigit;
    }
}
