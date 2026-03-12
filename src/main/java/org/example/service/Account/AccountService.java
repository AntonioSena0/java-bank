package org.example.service.Account;

import org.example.model.Account;
import org.example.model.Transaction;
import org.example.repository.AccountRepositoryImpl;

import javax.naming.InsufficientResourcesException;
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

    public void deposit(UUID account_id, double value) throws AccountNotFoundException{

        repository.deposit(account_id, value);

    }

    public void withdraw(UUID account_id, double value) throws InsufficientResourcesException, AccountNotFoundException{

        repository.withdraw(account_id, value);

    }

    public void transfer(UUID account_id, UUID to_account_id, double value) throws InsufficientResourcesException, AccountNotFoundException {

        repository.transfer(account_id, to_account_id, value);

    }

    public void delete(UUID id) throws AccountNotFoundException{

        repository.delete(id);

    }

    public Stack<Transaction> listTransactions(UUID id){

        return repository.listTransactions(id);

    }

}
