package org.example.controller;

import org.example.dto.AccountResponse;
import org.example.dto.TransactionResponse;
import org.example.mapper.TransactionMapper;
import org.example.repository.AccountRepositoryImpl;
import org.example.repository.PixKeyRepositoryImpl;
import org.example.repository.TransactionRepositoryImpl;
import org.example.service.TransactionService;

import javax.naming.InsufficientResourcesException;
import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    public void deposit(Long accountId, double value) throws AccountNotFoundException {
        service.deposit(accountId, value);
    }

    public void withdraw(Long accountId, double value)
            throws InsufficientResourcesException, AccountNotFoundException {
        service.withdraw(accountId, value);
    }

    public void transfer(Long fromAccountId, Long toAccountId, double value)
            throws InsufficientResourcesException, AccountNotFoundException {
        service.transfer(fromAccountId, toAccountId, value);
    }

    public TransactionResponse findTransaction(Long id) throws ClassNotFoundException {
        return service.findTransaction(id);
    }

    public List<TransactionResponse> findAll() {
        return service.findAll();
    }

    public List<TransactionResponse> listTransactionsByAccountId(Long id) {
        return service.listTransactionsByAccountId(id);
    }

}