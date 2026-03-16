package org.example.dto;

import org.example.enums.PixKeyType;

import java.time.LocalDate;

public record PixKeyResponse (

        String keyValue,
        PixKeyType type,
        LocalDate createdAt

) {
}
