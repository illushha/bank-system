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
                           @RequestParam double amount) {

        transactionService.transfer(fromId, toId, amount);
        return "redirect:/accounts";
    }
    @GetMapping("/transactions")
    public String history(Model model) {
        model.addAttribute("transactions", transactionRepository.findAll());
        return "transactions";
    }
}