package com.example.bankservice.controller;

import com.example.bankservice.DTO.TransferRequest;
import com.example.bankservice.model.Account;
import com.example.bankservice.service.TransferService;
import org.apache.catalina.connector.Request;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;

@RestController
public class AccountController {
    public TransferService transferService;

    public AccountController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/transfer")
    public void transferMoney(@RequestBody TransferRequest request){
        try {
            transferService.transferMoney(
                    request.getSenderAccountId(),
                    request.getReceiverAccountId(),
                    request.getAmount());
        } catch (AccountNotFoundException e) {

        }
    }

    @GetMapping("/accounts")
    public Iterable<Account> getAllAccounts(@RequestParam(required = false)String name){
        if(name == null){
            return transferService.getAllAcounts();
        }
        else {
           return transferService.findAccountByName(name);
        }
    }
}
