package org.example.mapper;

import org.example.dto.PixKeyRequest;
import org.example.dto.PixKeyResponse;
import org.example.model.PixKey;

public class PixKeyMapper {

    public PixKeyResponse toPixKeyResponse(PixKey key) {
        if (key == null) return null;

        return new PixKeyResponse(
                key.getKeyValue(),
                key.getType(),
                key.getCreatedAt()
        );
    }


}
