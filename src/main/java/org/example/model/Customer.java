package org.example.model;

import jakarta.persistence.*;
import org.example.enums.UserType;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 16)
    private String phone;

    @Column(nullable = false, unique = true, length = 15)
    private String document;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType role = UserType.CLIENT;

    @CreationTimestamp
    @Column(name = "created_at")
    LocalDate createdAt;

    public Customer() {
    }

    public Customer(String name, String email, String password, String phone, String document, UserType role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.document = document;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getDocument() {
        return document;
    }

    public UserType getRole() {
        return role;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public void setRole(UserType role) {
        this.role = role;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }
}