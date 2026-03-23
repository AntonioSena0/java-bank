package org.example.service;

import org.example.dto.AccountIdResponse;
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
import java.util.ArrayList;
import java.util.List;

public class PixKeyService {

    private final PixKeyRepositoryImpl repository;
    private final AccountRepositoryImpl accountRepository;
    private final PixKeyMapper pixKeyMapper;
    private final AccountMapper accountMapper;

    public PixKeyService(PixKeyRepositoryImpl repository, AccountRepositoryImpl accountRepository, PixKeyMapper pixKeyMapper, AccountMapper accountMapper) {
        this.repository = repository;
        this.accountRepository = accountRepository;
        this.pixKeyMapper = pixKeyMapper;
        this.accountMapper = accountMapper;
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

    public List<PixKeyResponse> findByAccountId(Long id) throws ClassNotFoundException{

        List<PixKey> pixKeysList = repository.findByAccountId(id);

        List<PixKeyResponse> pixKeyResponses = new ArrayList<>();

        pixKeysList.forEach(pixKey -> {
            pixKeyResponses.add(pixKeyMapper.toPixKeyResponse(pixKey));
        });

        return pixKeyResponses;

    }

    public AccountIdResponse findByPixKey(String key) throws AccountNotFoundException{

        return accountMapper.toAccountIdResponse(repository.findByPixKey(key));

    }

    public PixKeyResponse update(String newKey, Long id) throws ClassNotFoundException{

        return pixKeyMapper.toPixKeyResponse(repository.update(newKey, id));

    }

    public void delete(Long id) throws ClassNotFoundException {

        repository.delete(id);

    }

}
