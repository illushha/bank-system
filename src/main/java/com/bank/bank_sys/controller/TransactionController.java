package com.bank.bank_sys.controller;

import com.bank.bank_sys.repository.AccountRepository;
import com.bank.bank_sys.repository.TransactionRepository;
import com.bank.bank_sys.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TransactionController {

    private final TransactionService transactionService;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionController(TransactionService transactionService,
                                 AccountRepository accountRepository,
                                 TransactionRepository transactionRepository) {
        this.transactionService = transactionService;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @GetMapping("/transfer")
    public String form(Model model) {
        model.addAttribute("accounts", accountRepository.findAll());
        return "transfer";
    }

    @PostMapping("/transfer")
    public String transfer(@RequestParam Long fromId,
                           @RequestParam Long toId,
                           @RequestParam double amount,
                           Model model) {
        try {
            transactionService.transfer(fromId, toId, amount);
            model.addAttribute("message", "Переказ виконано успішно");
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
        }

        model.addAttribute("accounts", accountRepository.findAll());
        return "transfer";
    }
    @GetMapping("/transactions")
    public String history(Model model) {
        model.addAttribute("transactions", transactionRepository.findAll());
        return "transactions";
    }
}