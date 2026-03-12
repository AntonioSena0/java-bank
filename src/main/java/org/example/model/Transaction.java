package org.example.model;

import org.example.enums.TransactionType;

import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {

    private UUID id = UUID.randomUUID();
    private TransactionType type;
    private double amount;
    private LocalDateTime date;
    private UUID id_account;

    public Transaction(TransactionType type, double amount, UUID id_account) {
        this.type = type;
        this.amount = amount;
        this.id_account = id_account;
    }

    public UUID getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId_account() {
        return id_account;
    }

    public void setId_account(UUID id_account) {
        this.id_account = id_account;
    }
}
