package com.example.bankservice.service;

import com.example.bankservice.model.Account;
import com.example.bankservice.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.List;

@Service
public class TransferService {

    private final AccountRepository accountRepository;

    public TransferService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void transferMoney(long idSender, long idReceiver, BigDecimal amount) throws AccountNotFoundException {
        Account sender = accountRepository.findById(idSender)
                .orElseThrow(()->new AccountNotFoundException());

        Account receiver = accountRepository.findById(idReceiver)
                .orElseThrow(() -> new AccountNotFoundException());

        BigDecimal senderAmount = sender.getAmount().subtract(amount);
        BigDecimal receiverAmount = receiver.getAmount().add(amount);

        accountRepository.changeAmount(idSender,senderAmount);
        accountRepository.changeAmount(idReceiver, receiverAmount);

    }

    public Iterable<Account> getAllAcounts(){
        return accountRepository.findAll();
    }

    public List<Account> findAccountByName(String name){
        return accountRepository.findAccountsByName(name);
    }
}
