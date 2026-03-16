package org.example.service;

import org.example.mapper.CustomerMapper;
import org.example.dto.CustomerLoginRequest;
import org.example.dto.CustomerRequest;
import org.example.dto.CustomerResponse;
import org.example.model.Customer;
import org.example.repository.CustomerRepositoryImpl;

import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.LoginException;

public class CustomerService {

    private final CustomerRepositoryImpl repository;
    private final CustomerMapper mapper;

    public CustomerService(CustomerRepositoryImpl repository, CustomerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public CustomerResponse create(CustomerRequest request){

        return mapper.toCustomerResponse(repository.create(mapper.toCustomer(request)));

    }

    public CustomerResponse findById(Long id) throws AccountNotFoundException{

        return mapper.toCustomerResponse(repository.find(id));

    }

    public Customer findByEmail(String email) {

        return repository.findByEmail(email);

    }

    public Long login(CustomerLoginRequest request) throws LoginException {

        return repository.login(request);

    }

}