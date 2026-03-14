package org.example.model;

import org.example.enums.AccountType;
import org.example.enums.TransactionType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Stack;
import java.util.UUID;

public class Account {

    private UUID id = UUID.randomUUID();
    private double balance;
    private final AccountType accountType;
    private final UUID id_costumer;
    private final Stack<Transaction> transactionList = new Stack<>();

    public Account(double balance, AccountType accountType, UUID id_costumer) {
        this.balance = balance;
        this.accountType = accountType;
        this.id_costumer = id_costumer;
    }

    public UUID getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public UUID getId_costumer() {
        return id_costumer;
    }

    public Stack<Transaction> getTransactionList() {
        return transactionList;
    }

    public void deposit(UUID id, double value, TransactionType type) {

        if(value <= 0){
            throw new IllegalArgumentException("Valor inválido: " + value);
        }

        this.balance = this.getBalance() + value;
        transactionList.push(new Transaction(type, value, LocalDate.now() ,id));

    }

    public void withdraw(UUID id, double value, TransactionType type) {

        if(value <= 0){
            throw new IllegalArgumentException("Valor inválido: " + value);
        }

        if(value > this.getBalance()) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }

        this.balance = this.getBalance() - value;
        transactionList.push(new Transaction(type, -value, LocalDate.now(), id));

    }

}