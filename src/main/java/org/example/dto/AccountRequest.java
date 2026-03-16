package org.example.dto;

import org.example.enums.AccountType;
import org.example.model.Customer;

public record AccountRequest(

        double balance,
        AccountType type,
        Long customer_id

) {}