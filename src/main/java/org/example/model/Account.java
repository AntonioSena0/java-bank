package org.example.model;

import jakarta.persistence.*;
import org.example.enums.AccountType;
import org.example.enums.TransactionType;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double balance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "account_type")
    private AccountType accountType;

    @OneToOne(mappedBy = "account", cascade = CascadeType.PERSIST)
    @JoinColumn(unique = true, name = "pix_key")
    private PixKey pixKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer", nullable = false)
    private Customer customer;

    @CreationTimestamp
    @Column(name = "created_at")
    LocalDate createdAt;

    public Account() {
    }

    public Account(double balance, AccountType accountType, Customer customer) {
        this.balance = balance;
        this.accountType = accountType;
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setPixKey(PixKey pixKey) {
        this.pixKey = pixKey;
    }

    public PixKey getPixKey() {
        return pixKey;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void deposit(double value, TransactionType type) {

        if(value <= 0){
            throw new IllegalArgumentException("Valor inválido: " + value);
        }

        this.balance += value;
    }

    public void withdraw(double value, TransactionType type) {

        if(value <= 0){
            throw new IllegalArgumentException("Valor inválido: " + value);
        }

        if(value > this.getBalance()) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }

        this.balance -= value;
    }

    public void addPixKey(PixKey pixKey){

        this.pixKey = pixKey;
        pixKey.setAccount(this);

    }

}