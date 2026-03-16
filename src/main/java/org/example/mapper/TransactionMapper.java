package org.example.mapper;

import org.example.dto.AccountSummary;

import org.example.dto.TransactionResponse;
import org.example.model.Transaction;

public class TransactionMapper {

    public TransactionResponse toTransactionResponse(Transaction transaction) {
        if (transaction == null) return null;

        AccountSummary fromSummary = transaction.getFrom_account() != null
                ? new AccountSummary(transaction.getFrom_account().getId(),
                transaction.getFrom_account().getBalance())
                : null;

        AccountSummary toSummary = transaction.getTo_account() != null
                ? new AccountSummary(transaction.getTo_account().getId(),
                transaction.getTo_account().getBalance())
                : null;

        return new TransactionResponse(
                transaction.getId(),
                transaction.getType(),
                transaction.getAmount(),
                fromSummary,
                toSummary,
                transaction.getCreatedAt()
        );
    }
}

