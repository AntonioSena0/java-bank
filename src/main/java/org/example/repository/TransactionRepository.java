package org.example.repository;

import org.example.model.Transaction;

import javax.security.auth.login.AccountNotFoundException;
import java.util.UUID;

public interface TransactionRepository {

    Transaction findTransaction(UUID id) throws ClassNotFoundException;

}
