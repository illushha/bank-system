package com.bank.bank_sys.controller;

import com.bank.bank_sys.entity.Account;
import com.bank.bank_sys.entity.Client;
import com.bank.bank_sys.repository.AccountRepository;
import com.bank.bank_sys.repository.ClientRepository;
import com.bank.bank_sys.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AccountController {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;
    private final AccountService accountService;

    public AccountController(AccountRepository accountRepository, ClientRepository clientRepository,
                             AccountService accountService) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
        this.accountService = accountService;
    }

    @GetMapping("/accounts")
    public String getAccounts(Model model) {
        model.addAttribute("accounts", accountRepository.findAll());
        return "accounts";
    }

    @GetMapping("/accounts/add")
    public String addForm(Model model) {
        model.addAttribute("account", new Account());
        model.addAttribute("clients", clientRepository.findAll());
        return "add-account";
    }

    @PostMapping("/accounts")
    public String save(@RequestParam String number,
                       @RequestParam double balance,
                       @RequestParam Long clientId) {

        Client client = clientRepository.findById(clientId).orElse(null);

        Account account = new Account();
        account.setNumber(number);
        account.setBalance(balance);
        account.setClient(client);

        accountService.save(account);

        return "redirect:/accounts";
    }
}