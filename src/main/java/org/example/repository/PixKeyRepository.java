package org.example.repository;

import org.example.model.Account;
import org.example.model.PixKey;

import javax.management.openmbean.KeyAlreadyExistsException;
import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

public interface PixKeyRepository {

    PixKey create(PixKey request);
    List<PixKey> findByAccountId(Long id) throws ClassNotFoundException;
    Account findByPixKey(String key) throws AccountNotFoundException;
    PixKey update(String newKey, Long id) throws ClassNotFoundException, KeyAlreadyExistsException;
    void delete(Long id) throws ClassNotFoundException;

}
