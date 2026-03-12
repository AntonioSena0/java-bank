package org.example.repository;

import org.example.model.Transaction;

import java.util.HashMap;
import java.util.UUID;

public class TransactionRepositoryImpl implements TransactionRepository{

    HashMap<UUID, Transaction> transactions = new HashMap<>();

    @Override
    public Transaction findTransaction(UUID id) throws ClassNotFoundException {

        Transaction transaction = transactions.get(id);

        if(transaction == null){
            throw new ClassNotFoundException();
        }

        return transaction;

    }

}
