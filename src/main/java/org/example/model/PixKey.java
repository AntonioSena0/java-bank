package org.example.model;

import jakarta.persistence.*;
import org.example.enums.PixKeyType;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "pix_keys")
public class PixKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "key_value", unique = true, length = 100, nullable = false)
    private String keyValue;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private PixKeyType type;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false, unique = true)
    private Account account;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDate createdAt;

    public PixKey() {
    }

    public PixKey(String keyValue, PixKeyType type, Account account) {
        this.keyValue = keyValue;
        this.type = type;
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public PixKeyType getType() {
        return type;
    }

    public void setType(PixKeyType type) {
        this.type = type;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}