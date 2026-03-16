package org.example.dto;

import org.example.enums.AccountType;

import java.time.LocalDate;

public record AccountResponse(

        Long id,
        CustomerResponse customer,
        PixKeyResponse pixKey,
        AccountType type,
        double balance,
        LocalDate createdAt


) {
}
