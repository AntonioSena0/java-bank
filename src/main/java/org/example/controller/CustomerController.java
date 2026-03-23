package org.example.controller;

import org.example.dto.CustomerLoginRequest;
import org.example.dto.CustomerRequest;
import org.example.dto.CustomerResponse;
import org.example.enums.UserType;
import org.example.model.Customer;
import org.example.service.CustomerService;

import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.LoginException;

public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    public CustomerResponse create(CustomerRequest request) {
        try {

            return service.create(request);

        } catch (Exception e) {
            System.out.println("Erro ao criar cliente: " + e.getMessage());
            return null;
        }
    }

    public Long login(CustomerLoginRequest request) {
        try {
            return service.login(request);
        } catch (LoginException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("Erro no login: " + e.getMessage());
            return null;
        }
    }

    public CustomerResponse find(Long id) {
        try {
            return service.findById(id);
        } catch (AccountNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("Erro ao buscar cliente: " + e.getMessage());
            return null;
        }
    }

    public Customer findByEmail(String email) {
        try {
            return service.findByEmail(email);
        } catch (Exception e) {
            System.out.println("Erro ao buscar por email: " + e.getMessage());
            return null;
        }
    }

    public CustomerResponse update(CustomerRequest request, Long id) {
        try {
            return service.update(request, id);
        } catch (AccountNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("Erro ao atualizar cliente: " + e.getMessage());
            return null;
        }
    }

    public void delete(Long id) {
        try {
            service.delete(id);
            System.out.println("Cliente deletado com sucesso.");
        } catch (AccountNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao deletar cliente: " + e.getMessage());
        }
    }

    public void createAdminIfNotExists() {
        try {
            Customer existing = service.findByEmail("admin@gmail.com");
            if (existing != null) {
                return;
            }

            CustomerRequest request = new CustomerRequest(
                    "Admin",
                    "admin@gmail.com",
                    "admin123456",
                    "000.000.000-00",
                    "(11) 12345-1234",
                    UserType.ADMIN
            );

            service.create(request);

        } catch (Exception e) {
            System.out.println("Erro ao criar admin: " + e.getMessage());
        }
    }
}