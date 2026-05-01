package com.bank.bank_sys.controller;

import com.bank.bank_sys.repository.AccountRepository;
import com.bank.bank_sys.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TransactionController {

    private final TransactionService transactionService;
    private final AccountRepository accountRepository;

    public TransactionController(TransactionService transactionService,
                                 AccountRepository accountRepository) {
        this.transactionService = transactionService;
        this.accountRepository = accountRepository;
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
}