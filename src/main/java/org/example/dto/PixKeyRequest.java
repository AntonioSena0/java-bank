package org.example.dto;

import org.example.enums.PixKeyType;

public record PixKeyRequest (

        String keyValue,
        PixKeyType type,
        Long account_id

) {
}