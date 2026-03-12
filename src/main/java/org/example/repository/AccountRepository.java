package org.example.repository;

import org.example.model.Account;
import org.example.model.Transaction;

import javax.naming.InsufficientResourcesException;
import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.Stack;
import java.util.UUID;

public interface AccountRepository {

    void deposit(UUID id, double value) throws AccountNotFoundException;
    void withdraw(UUID id, double value) throws InsufficientResourcesException, AccountNotFoundException;
    void transfer(UUID id, UUID to_id, double value) throws InsufficientResourcesException, AccountNotFoundException;
    Account create(Account account);
    List<UUID> login(UUID customer_id) throws AccountNotFoundException;
    Account find(UUID key) throws AccountNotFoundException;
    public List<Account> findByIdCustomer(UUID key) throws AccountNotFoundException;
    void delete(UUID id) throws AccountNotFoundException;
    Stack<Transaction> listTransactions(UUID id);

}
