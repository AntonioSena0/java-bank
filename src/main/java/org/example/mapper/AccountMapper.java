package org.example.mapper;

import org.example.dto.AccountIdResponse;
import org.example.dto.AccountResponse;
import org.example.dto.PixKeyResponse;
import org.example.model.Account;
import org.example.model.PixKey;

public class AccountMapper {

    private final CustomerMapper customerMapper;

    public AccountMapper(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    public AccountResponse toAccountResponse(Account account) {
        if (account == null) return null;

        PixKeyResponse pixResponse = null;

        return new AccountResponse(
                account.getId(),
                customerMapper.toCustomerResponse(account.getCustomer()),
                account.getAccountType(),
                account.getBalance(),
                account.getCreatedAt()
        );
    }

    public AccountIdResponse toAccountIdResponse(Account account){

        return new AccountIdResponse(
                account.getId()
        );

    }


}
