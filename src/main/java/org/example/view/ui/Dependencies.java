package org.example.view.ui;

import org.example.controller.*;
import org.example.mapper.*;
import org.example.repository.*;
import org.example.service.*;

public class Dependencies {

    private static CustomerController customerController;
    private static AccountController accountController;
    private static PixKeyController pixKeyController;
    private static TransactionController transactionController;

    public static CustomerController customerController() {
        if (customerController == null) {
            CustomerRepositoryImpl customerRepository = new CustomerRepositoryImpl();
            CustomerMapper customerMapper = new CustomerMapper();
            CustomerService customerService = new CustomerService(customerRepository, customerMapper);
            customerController = new CustomerController(customerService);
        }
        return customerController;
    }

    public static AccountController accountController() {
        if (accountController == null) {
            AccountRepositoryImpl accountRepository = new AccountRepositoryImpl();
            CustomerRepositoryImpl customerRepository = new CustomerRepositoryImpl();
            CustomerMapper customerMapper = new CustomerMapper();
            AccountMapper accountMapper = new AccountMapper(customerMapper);
            AccountService accountService = new AccountService(accountRepository, accountMapper, customerRepository);
            accountController = new AccountController(accountService);
        }
        return accountController;
    }

    public static PixKeyController pixKeyController() {
        if (pixKeyController == null) {
            PixKeyRepositoryImpl pixKeyRepository = new PixKeyRepositoryImpl();
            AccountRepositoryImpl accountRepository = new AccountRepositoryImpl();
            CustomerMapper customerMapper = new CustomerMapper();
            AccountMapper accountMapper = new AccountMapper(customerMapper);
            PixKeyMapper pixKeyMapper = new PixKeyMapper();
            PixKeyService pixKeyService = new PixKeyService(pixKeyRepository, accountRepository, pixKeyMapper, accountMapper);
            pixKeyController = new PixKeyController(pixKeyService);
        }
        return pixKeyController;
    }

    public static TransactionController transactionController() {
        if (transactionController == null) {
            TransactionRepositoryImpl transactionRepository = new TransactionRepositoryImpl();
            TransactionService transactionService = new TransactionService(transactionRepository);

            transactionController = new TransactionController(transactionService);
        }
        return transactionController;
    }
}