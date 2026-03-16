package org.example.dto;

import org.example.enums.UserType;

public record CustomerRequest(

        String name,
        String email,
        String password,
        String phone,
        String document,
        UserType role

) {
}
