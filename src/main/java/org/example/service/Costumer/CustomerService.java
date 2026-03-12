package org.example.service.Costumer;

import org.example.model.Customer;
import org.example.repository.CustomerRepositoryImpl;

import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.LoginException;
import java.util.UUID;

public class CustomerService {

    private final CustomerRepositoryImpl repository;

    public CustomerService(CustomerRepositoryImpl costumerRepository) {
        this.repository = costumerRepository;
    }

    public Customer create(Customer request){

        return repository.create(request);

    }

    public UUID login(Customer request) throws AccountNotFoundException, LoginException {

        return repository.login(request);

    }

}