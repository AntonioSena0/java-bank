package org.example.service;

import org.example.model.Transaction;
import org.example.repository.TransactionRepositoryImpl;

import javax.naming.InsufficientResourcesException;
import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.UUID;

public class TransactionService {

    private final TransactionRepositoryImpl repository;

    public TransactionService(TransactionRepositoryImpl transactionRepository) {
        this.repository = transactionRepository;
    }

    public void deposit(UUID account_id, double value) throws AccountNotFoundException{

        repository.deposit(account_id, value);

    }

    public void withdraw(UUID account_id, double value) throws InsufficientResourcesException, AccountNotFoundException{

        repository.withdraw(account_id, value);

    }

    public void transfer(UUID account_id, UUID to_account_id, double value) throws InsufficientResourcesException, AccountNotFoundException {

        repository.transfer(account_id, to_account_id, value);

    }

    public Transaction findTransaction(UUID id) throws ClassNotFoundException {

        return repository.findTransaction(id);

    }

    public List<Transaction> findAll() {

        return repository.findAll();

    }

}
