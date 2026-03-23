package org.example.controller;

import org.example.dto.AccountIdResponse;
import org.example.dto.PixKeyRequest;
import org.example.dto.PixKeyResponse;
import org.example.mapper.AccountMapper;
import org.example.mapper.PixKeyMapper;
import org.example.model.Account;
import org.example.model.PixKey;
import org.example.repository.AccountRepositoryImpl;
import org.example.repository.PixKeyRepositoryImpl;
import org.example.service.PixKeyService;

import javax.security.auth.login.AccountNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class PixKeyController {

    private final PixKeyService service;

    public PixKeyController(PixKeyService service) {
        this.service = service;
    }

    public PixKeyResponse createPixKey(PixKeyRequest request) {
        try {

            return service.create(request);

        } catch (AccountNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("Erro ao criar chave PIX: " + e.getMessage());
            return null;
        }
    }

    public List<PixKeyResponse> findByAccountId(Long id) {
        List<PixKeyResponse> pixKeyResponses = new ArrayList<>();
        try {

            return service.findByAccountId(id);

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return pixKeyResponses;
        } catch (Exception e) {
            System.out.println("Erro ao buscar chaves PIX da conta: " + e.getMessage());
            return pixKeyResponses;
        }
    }

    public AccountIdResponse findByPixKey(String key) {
        try {
            return service.findByPixKey(key);
        } catch (AccountNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("Erro ao buscar conta pela chave PIX: " + e.getMessage());
            return null;
        }
    }

    public PixKeyResponse updatePixKey(String newKey, Long id) {
        try {

            return service.update(newKey, id);

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("Erro ao atualizar chave PIX: " + e.getMessage());
            return null;
        }
    }

    public void deletePixKey(Long id) {
        try {
            service.delete(id);
            System.out.println("Chave PIX deletada com sucesso.");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao deletar chave PIX: " + e.getMessage());
        }
    }
}
