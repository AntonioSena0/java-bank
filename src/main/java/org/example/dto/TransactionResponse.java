package org.example.dto;

import org.example.enums.TransactionType;

import java.time.LocalDate;

public record TransactionResponse(

        Long id,
        TransactionType type,
        double amount,
        AccountSummary from_account,
        AccountSummary to_account,
        LocalDate createdAt

) {
}
