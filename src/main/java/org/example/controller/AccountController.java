package org.example.controller;

import org.example.dto.AccountRequest;
import org.example.dto.AccountResponse;
import org.example.service.AccountService;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    public AccountResponse create(AccountRequest request) {
        try {

            return service.create(request);

        } catch (AccountNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("Erro ao criar conta: " + e.getMessage());
            return null;
        }
    }

    public List<AccountResponse> findAll() {

        return service.findAll();

    }

    public AccountResponse find(Long id) {
        try {
            return service.find(id);
        } catch (AccountNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("Erro ao buscar conta: " + e.getMessage());
            return null;
        }
    }

    public List<AccountResponse> findByCustomerId(Long id) {
        try {
            return service.findByCustomerId(id);
        } catch (AccountNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("Erro ao buscar contas do cliente: " + e.getMessage());
            return null;
        }
    }

    public List<Long> login(Long idCustomer) {
        try {
            return service.login(idCustomer);
        } catch (AccountNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("Erro no login de contas: " + e.getMessage());
            return null;
        }
    }

    public void delete(Long id) {
        try {
            service.delete(id);
            System.out.println("Conta deletada com sucesso.");
        } catch (AccountNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao deletar conta: " + e.getMessage());
        }
    }
}