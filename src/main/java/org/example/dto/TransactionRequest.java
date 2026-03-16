package org.example.dto;

import org.example.enums.TransactionType;

public record TransactionRequest (

        TransactionType type,
        double amount,
        Long from_account,
        Long to_account

) { }
