package org.example.repository;

import org.example.dto.TransactionResponse;
import org.example.model.Transaction;

import javax.naming.InsufficientResourcesException;
import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

public interface TransactionRepository {

    void deposit(Long id, double value) throws AccountNotFoundException;
    void withdraw(Long id, double value) throws InsufficientResourcesException, AccountNotFoundException;
    void transfer(Long id, Long to_id, double value) throws InsufficientResourcesException, AccountNotFoundException;
    TransactionResponse findTransaction(Long id) throws ClassNotFoundException;
    List<TransactionResponse> findAll();
    List<TransactionResponse> listTransactionsByAccountId(Long id);

}