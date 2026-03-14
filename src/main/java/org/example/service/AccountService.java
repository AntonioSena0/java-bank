package org.example.service;

import org.example.model.Account;
import org.example.model.Transaction;
import org.example.repository.AccountRepositoryImpl;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.Stack;
import java.util.UUID;

public class AccountService {

    private final AccountRepositoryImpl repository;

    public AccountService(AccountRepositoryImpl accountRepository) {
        this.repository = accountRepository;
    }

    public Account create(Account account) {

        return repository.create(account);

    }

    public Account find(UUID id) throws AccountNotFoundException{
        return repository.find(id);
    }

    public List<Account> findByCustomerId(UUID id) throws AccountNotFoundException{
        return repository.findByIdCustomer(id);
    }

    public List<UUID> login(UUID id_customer) throws AccountNotFoundException{

        return repository.login(id_customer);

    }

    public void delete(UUID id) throws AccountNotFoundException{

        repository.delete(id);

    }

    public Stack<Transaction> listTransactions(UUID id){

        return repository.listTransactions(id);

    }

}
