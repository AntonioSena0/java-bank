package org.example.model;

import jakarta.persistence.*;
import org.example.enums.TransactionType;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Column(nullable = false)
    private double amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_account", nullable = false)
    private Account from_account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_account_id", nullable = true)
    private Account to_account;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDate createdAt;

    public Transaction() {
    }

    public Transaction(TransactionType type, double amount, Account from_account) {
        this.type = type;
        this.amount = amount;
        this.from_account = from_account;
    }

    public Transaction(TransactionType type, double amount, Account from_account, Account to_account) {
        this.type = type;
        this.amount = amount;
        this.from_account = from_account;
        this.to_account = to_account;
    }

    public Long getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getFrom_account() {
        return from_account;
    }

    public Account getTo_account() {
        return to_account;
    }
}
