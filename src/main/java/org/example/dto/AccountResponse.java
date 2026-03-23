package org.example.dto;

import org.example.enums.AccountType;

import java.time.LocalDate;

public record AccountResponse(

        Long id,
        CustomerResponse customer,
        AccountType type,
        double balance,
        LocalDate createdAt


) {
}
