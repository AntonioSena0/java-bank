package org.example.repository;

import org.example.enums.TransactionType;
import org.example.model.Account;
import org.example.model.Transaction;

import javax.naming.InsufficientResourcesException;
import javax.security.auth.login.AccountNotFoundException;
import java.util.*;

public class AccountRepositoryImpl implements AccountRepository{

    private final HashMap<UUID, Account> accounts = new HashMap<>();
    private final HashMap<UUID, List<Account>> accountsLogin = new HashMap<>();

    @Override
    public Account create(Account account) {

        if(account.getBalance() < 0){
            System.out.println("Seu saldo não pode ser negativo");
            return null;
        }

        Account newAccount = new Account(account.getBalance(), account.getAccountType(), account.getId_costumer());

        accounts.put(newAccount.getId(), newAccount);
        accountsLogin.computeIfAbsent(newAccount.getId_costumer(), k -> new ArrayList<>()).add(newAccount);

        return newAccount;

    }

    @Override
    public Account find(UUID key) throws AccountNotFoundException {

        if (accounts.get(key) == null) {
            throw new AccountNotFoundException();
        }

        return accounts.get(key);

    }

    @Override
    public List<Account> findByIdCustomer(UUID key) throws AccountNotFoundException {

        List<Account> list = accountsLogin.get(key);

        if (list == null || list.isEmpty()) {
            throw new AccountNotFoundException("Você ainda não tem contas");
        }

        return list;

    }


    @Override
    public List<UUID> login(UUID customer_id) throws AccountNotFoundException {

        List<Account> existAccount = findByIdCustomer(customer_id);

        if(existAccount.isEmpty()){
            throw new AccountNotFoundException("Você ainda não tem contas, cadastre uma");
        }

        List<UUID> accounts = new ArrayList<>();
        existAccount.forEach(account -> {
            accounts.add(account.getId());
        });

        return accounts;

    }

    public List<Account> findAll() {

        return new ArrayList<>(accounts.values());

    }

    @Override
    public void delete(UUID id) throws AccountNotFoundException {

        Account existingAccounnt = this.find(id);

        if(existingAccounnt == null){
            throw new AccountNotFoundException("Conta inexistente");
        }

        accounts.remove(id);

    }

    @Override
    public Stack<Transaction> listTransactions(UUID id) {

        return accounts.get(id).getTransactionList();

    }
}
