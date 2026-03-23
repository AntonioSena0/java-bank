package org.example.dto;

import org.example.enums.AccountType;

public record AccountRequest(

        double balance,
        AccountType type,
        Long customer_id

) {}