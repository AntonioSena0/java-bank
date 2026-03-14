package org.example.repository;

import org.example.enums.TransactionType;
import org.example.model.Account;
import org.example.model.Transaction;

import javax.naming.InsufficientResourcesException;
import javax.security.auth.login.AccountNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class TransactionRepositoryImpl implements TransactionRepository{

    HashMap<UUID, Transaction> transactions = new HashMap<>();
    private final AccountRepositoryImpl repository;

    public TransactionRepositoryImpl(AccountRepositoryImpl repository) {
        this.repository = repository;
    }

    @Override
    public void deposit(UUID id, double value) throws AccountNotFoundException{

        Account account = repository.find(id);

        if(account == null){
            throw new AccountNotFoundException("Conta não existente");
        }

        Transaction transaction = new Transaction(TransactionType.DEPOSIT, value, LocalDate.now(), id);
        transactions.put(transaction.getId(), transaction);

        account.deposit(account.getId(), value, TransactionType.DEPOSIT);

    }

    @Override
    public void withdraw(UUID id, double value) throws InsufficientResourcesException, AccountNotFoundException{

        Account account = repository.find(id);

        if(account == null){
            throw new AccountNotFoundException("Conta não existente");
        }

        if(value > account.getBalance()) {
            throw new InsufficientResourcesException("Saldo insuficiente");
        }

        Transaction transaction = new Transaction(TransactionType.WITHDRAW, value, LocalDate.now(), id);
        transactions.put(transaction.getId(), transaction);

        account.withdraw(id, value, TransactionType.WITHDRAW);

    }

    @Override
    public void transfer(UUID id, UUID to_id, double value) throws InsufficientResourcesException, AccountNotFoundException {

        if(value <= 0){
            throw new IllegalArgumentException("Valor inválido");
        }
        if(value > repository.find(id).getBalance()){
            throw new InsufficientResourcesException("Saldo insuficiente");
        }
        if(id.equals(to_id)){
            throw new IllegalArgumentException("Você não pode transferir para a mesma conta");
        }

        Account from = repository.find(id);
        Account to = repository.find(to_id);

        if(from == null || to == null){
            throw new AccountNotFoundException("Uma das contas não existe");
        }

        Transaction transaction = new Transaction(TransactionType.TRANSFER, value, LocalDate.now(), id, to_id);
        transactions.put(transaction.getId(), transaction);

        from.withdraw(id, value, TransactionType.TRANSFER);
        to.deposit(to_id, value, TransactionType.TRANSFER);

    }

    @Override
    public Transaction findTransaction(UUID id) throws ClassNotFoundException {

        Transaction transaction = transactions.get(id);

        if(transaction == null){
            throw new ClassNotFoundException();
        }

        return transaction;

    }

    @Override
    public List<Transaction> findAll() {

        List<Transaction> transactionsList = new ArrayList<>();

        if(transactions.isEmpty()){
            throw new RuntimeException("Não foram feitas transações ainda");
        }

        transactions.forEach((uuid, transaction) -> {
            transactionsList.add(transaction);
        });

        return transactionsList;
    }
}
