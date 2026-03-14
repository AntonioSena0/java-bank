package org.example.repository;

import org.example.model.Transaction;

import javax.naming.InsufficientResourcesException;
import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.UUID;

public interface TransactionRepository {

    void deposit(UUID id, double value) throws AccountNotFoundException;
    void withdraw(UUID id, double value) throws InsufficientResourcesException, AccountNotFoundException;
    void transfer(UUID id, UUID to_id, double value) throws InsufficientResourcesException, AccountNotFoundException;
    Transaction findTransaction(UUID id) throws ClassNotFoundException;
    List<Transaction> findAll();

}
