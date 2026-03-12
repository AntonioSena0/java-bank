package org.example.model;

import java.util.UUID;

public class Customer {

    private UUID id = UUID.randomUUID();
    private String name;
    private String email;
    private String password;
    private String document;

    public Customer(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Customer(String name, String email, String password, String document) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.document = document;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

}