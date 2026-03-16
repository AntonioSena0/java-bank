package org.example.repository;

import org.example.model.Account;
import org.example.model.PixKey;

import javax.security.auth.login.AccountNotFoundException;

public interface PixKeyRepository {

    PixKey create(PixKey request);

}
