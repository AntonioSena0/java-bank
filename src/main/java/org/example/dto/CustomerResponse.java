package org.example.dto;

import org.example.enums.UserType;

import java.time.LocalDate;

public record CustomerResponse(

        Long id,
        String name,
        String email,
        String phone,
        String document,
        UserType role,
        LocalDate createdAt

) {
}
