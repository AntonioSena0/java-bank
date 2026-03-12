package org.example.controller;

import org.example.model.Transaction;
import org.example.service.Transaction.TransactionService;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.Scanner;

public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService transactionService) {
        this.service = transactionService;
    }



}
