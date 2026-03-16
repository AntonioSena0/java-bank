package org.example.service;

import org.example.mapper.TransactionMapper;
import org.example.dto.TransactionResponse;
import org.example.repository.TransactionRepositoryImpl;

import javax.naming.InsufficientResourcesException;
import javax.security.auth.login.AccountNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class TransactionService {

    private final TransactionRepositoryImpl repository;
    private final TransactionMapper mapper;

    public TransactionService(TransactionRepositoryImpl repository, TransactionMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public void deposit(Long account_id, double value) throws AccountNotFoundException{

        repository.deposit(account_id, value);

    }

    public void withdraw(Long account_id, double value) throws InsufficientResourcesException, AccountNotFoundException{

        repository.withdraw(account_id, value);

    }

    public void transfer(Long account_id, Long to_account_id, double value) throws InsufficientResourcesException, AccountNotFoundException {

        repository.transfer(account_id, to_account_id, value);

    }

    public TransactionResponse findTransaction(Long id) throws ClassNotFoundException {

        return repository.findTransaction(id);

    }

    public List<TransactionResponse> findAll() {

        return repository.findAll();

    }

    public List<TransactionResponse> listTransactionsByAccountId(Long id){

        return repository.listTransactionsByAccountId(id);

    }

}
