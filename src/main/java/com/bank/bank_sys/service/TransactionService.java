package com.bank.bank_sys.service;

import com.bank.bank_sys.entity.Account;
import com.bank.bank_sys.entity.Transaction;
import com.bank.bank_sys.repository.AccountRepository;
import com.bank.bank_sys.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionService(AccountRepository accountRepository,
                              TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public void transfer(Long fromId, Long toId, double amount) {

        Account from = accountRepository.findById(fromId).orElseThrow();
        Account to = accountRepository.findById(toId).orElseThrow();

        if (amount <= 0) {
            throw new RuntimeException("Сума переказу має бути більшою за 0");
        }

        if (from.getId().equals(to.getId())) {
            throw new RuntimeException("Не можна переказувати кошти на той самий рахунок");
        }

        if (from.getBalance() < amount) {
            throw new RuntimeException("Недостатньо коштів на рахунку");
        }

        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);

        accountRepository.save(from);
        accountRepository.save(to);

        Transaction tx = new Transaction();
        tx.setFromAccount(from);
        tx.setToAccount(to);
        tx.setAmount(amount);

        transactionRepository.save(tx);
    }
}