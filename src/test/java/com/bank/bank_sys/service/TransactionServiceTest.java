package com.bank.bank_sys.service;

import com.bank.bank_sys.entity.Account;
import com.bank.bank_sys.repository.AccountRepository;
import com.bank.bank_sys.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Test
    void shouldTransferMoneyBetweenAccounts() {
        Account from = new Account();
        from.setId(1L);
        from.setBalance(1000);

        Account to = new Account();
        to.setId(2L);
        to.setBalance(500);

        AccountRepository accountRepository = Mockito.mock(AccountRepository.class);
        TransactionRepository transactionRepository = Mockito.mock(TransactionRepository.class);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(from));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(to));

        TransactionService transactionService =
                new TransactionService(accountRepository, transactionRepository);

        transactionService.transfer(1L, 2L, 300);

        assertEquals(700, from.getBalance());
        assertEquals(800, to.getBalance());

        verify(accountRepository, times(1)).save(from);
        verify(accountRepository, times(1)).save(to);
        verify(transactionRepository, times(1)).save(any());
    }

    @Test
    void shouldThrowExceptionWhenNotEnoughMoney() {
        Account from = new Account();
        from.setId(1L);
        from.setBalance(100);

        Account to = new Account();
        to.setId(2L);
        to.setBalance(500);

        AccountRepository accountRepository = Mockito.mock(AccountRepository.class);
        TransactionRepository transactionRepository = Mockito.mock(TransactionRepository.class);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(from));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(to));

        TransactionService transactionService =
                new TransactionService(accountRepository, transactionRepository);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                transactionService.transfer(1L, 2L, 300)
        );

        assertTrue(exception.getMessage().contains("Недостатньо"));
    }
}