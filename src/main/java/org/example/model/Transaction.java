package org.example.model;

import org.example.enums.TransactionType;

import java.time.LocalDate;
import java.util.UUID;

public class Transaction {

    private UUID id = UUID.randomUUID();
    private TransactionType type;
    private double amount;
    private LocalDate date;
    private UUID id_account;
    private UUID to_account;

    public Transaction(TransactionType type, double amount, LocalDate date, UUID id_account) {
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.id_account = id_account;
    }

    public Transaction(TransactionType type, double amount, LocalDate date, UUID id_account, UUID to_account) {
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.id_account = id_account;
        this.to_account = to_account;
    }

    public UUID getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId_account() {
        return id_account;
    }

    public UUID getTo_account() {
        return to_account;
    }
}
