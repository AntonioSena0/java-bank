package org.example.service;

import org.example.mapper.AccountMapper;
import org.example.mapper.PixKeyMapper;
import org.example.dto.AccountResponse;
import org.example.dto.PixKeyRequest;
import org.example.dto.PixKeyResponse;
import org.example.model.Account;
import org.example.model.PixKey;
import org.example.repository.AccountRepositoryImpl;
import org.example.repository.PixKeyRepositoryImpl;

import javax.security.auth.login.AccountNotFoundException;

public class PixKeyService {

    private final PixKeyRepositoryImpl repository;
    private final AccountRepositoryImpl accountRepository;
    private final PixKeyMapper pixKeyMapper;

    public PixKeyService(PixKeyRepositoryImpl repository, AccountRepositoryImpl accountRepository, PixKeyMapper pixKeyMapper) {
        this.repository = repository;
        this.accountRepository = accountRepository;
        this.pixKeyMapper = pixKeyMapper;
    }

    public PixKeyResponse create(PixKeyRequest request) throws AccountNotFoundException{

        accountRepository.find(request.account_id());

        PixKey key = new PixKey();
        key.setKeyValue(request.keyValue());
        key.setType(request.type());

        Account accRef = new Account();
        accRef.setId(request.account_id());
        key.setAccount(accRef);

        PixKey saved = repository.create(key);

        return pixKeyMapper.toPixKeyResponse(saved);

    }

}
