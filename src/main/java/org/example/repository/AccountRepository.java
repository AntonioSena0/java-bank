package org.example.repository;

import org.example.dto.AccountIdResponse;
import org.example.dto.AccountRequest;
import org.example.dto.AccountResponse;
import org.example.model.Account;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

public interface AccountRepository {

    Account create(Account request);
    List<Long> login(Long customer_id) throws AccountNotFoundException;
    AccountResponse find(Long key) throws AccountNotFoundException;
    List<Account> findAll();
    List<Account> findByIdCustomer(Long key) throws AccountNotFoundException;
    Account findByPixKey(String key) throws  AccountNotFoundException;

}