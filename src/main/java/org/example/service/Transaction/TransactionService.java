package org.example.service.Transaction;

import org.example.model.Transaction;
import org.example.repository.TransactionRepositoryImpl;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.UUID;

public class TransactionService {

    private final TransactionRepositoryImpl repository;

    public TransactionService(TransactionRepositoryImpl transactionRepository) {
        this.repository = transactionRepository;
    }

    public Transaction findTransaction(UUID id) throws ClassNotFoundException {

        return repository.findTransaction(id);

    }

}
