package org.example.repository;

import org.example.dto.CustomerLoginRequest;
import org.example.dto.CustomerRequest;
import org.example.dto.CustomerResponse;
import org.example.model.Customer;

import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.LoginException;

public interface CustomerRepository {

    Customer create(Customer request);
    Customer findByName(String name);
    Customer findByEmail(String email) throws AccountNotFoundException;
    Customer find(Long id) throws AccountNotFoundException;
    Long login(CustomerLoginRequest request) throws LoginException;
    Customer update(Customer request, Long id) throws AccountNotFoundException;
    void delete(Long id) throws AccountNotFoundException;

}