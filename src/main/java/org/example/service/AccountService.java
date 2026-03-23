package org.example.service;

import org.example.dto.AccountRequest;
import org.example.mapper.AccountMapper;
import org.example.dto.AccountResponse;
import org.example.model.Account;
import org.example.repository.AccountRepositoryImpl;
import org.example.repository.CustomerRepositoryImpl;

import javax.security.auth.login.AccountNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class AccountService {

    private final AccountRepositoryImpl repository;
    private final AccountMapper mapper;
    private final CustomerRepositoryImpl customerRepository;

    public AccountService(AccountRepositoryImpl repository, AccountMapper mapper, CustomerRepositoryImpl customerRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.customerRepository = customerRepository;
    }

    public AccountResponse create(AccountRequest request) throws AccountNotFoundException{

        Account account = new Account(request.balance(), request.type(), customerRepository.find(request.customer_id()));

        return mapper.toAccountResponse(repository.create(account));

    }

    public List<AccountResponse> findAll() {

        List<AccountResponse> accountResponses = new ArrayList<>();

        repository.findAll().forEach(account -> {
            accountResponses.add(mapper.toAccountResponse(account));
        });

        return accountResponses;

    }

    public AccountResponse find(Long id) throws AccountNotFoundException{

        return repository.find(id);

    }

    public List<AccountResponse> findByCustomerId(Long id) throws AccountNotFoundException{

        List<AccountResponse> accounts = new ArrayList<>();

        repository.findByIdCustomer(id).forEach(account -> {
            accounts.add(mapper.toAccountResponse(account));
        });

        return accounts;

    }

    public List<Long> login(Long id_customer) throws AccountNotFoundException{

        return repository.login(id_customer);

    }

    public void delete(Long id) throws AccountNotFoundException {

        repository.delete(id);

    }

}
