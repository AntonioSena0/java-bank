package org.example.repository;

import org.example.model.Customer;

import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.LoginException;
import java.util.UUID;

public interface CustomerRepository {

    Customer create(Customer customer);
    Customer findByEmail(String email) throws AccountNotFoundException;
    UUID login(Customer customer) throws AccountNotFoundException, LoginException;

}