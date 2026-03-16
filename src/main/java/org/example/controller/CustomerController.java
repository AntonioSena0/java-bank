package org.example.controller;

import org.example.dto.CustomerLoginRequest;
import org.example.dto.CustomerRequest;
import org.example.dto.CustomerResponse;
import org.example.enums.UserType;
import org.example.model.Customer;
import org.example.service.CustomerService;

import java.util.Scanner;

public class CustomerController {

    private final CustomerService service;
    private final Scanner sc;

    public CustomerController(CustomerService service, Scanner sc) {
        this.service = service;
        this.sc = sc;
    }

    public Long showMenuRegister(){

        System.out.println("Insira o seu nome");
        String name = sc.nextLine();
        System.out.println("Insira seu email");
        String email = sc.nextLine();
        while (!email.contains("@gmail.com")){
            System.out.println("Entrada inválida. Digite um email válido (Ex: joao@gmail.com)");
            email = sc.nextLine();
        }
        System.out.println("Insira sua senha");
        String password = sc.nextLine();
        System.out.println("Insira seu CPF");
        String document = sc.nextLine();
        System.out.println("Insira seu telefone");
        String phone = sc.nextLine();

        CustomerResponse newCustomer = this.create(new CustomerRequest(name, email, password, phone, document, UserType.CLIENT));

        return newCustomer.id();

    }

    public Long showMenuLogin(){

        System.out.println("Insira seu email");
        String email = sc.nextLine();
        while (!email.contains("@gmail.com")){
            System.out.println("Entrada inválida. Digite um email válido (Ex: joao@gmail.com)");
            email = sc.nextLine();
        }
        System.out.println("Insira sua senha");
        String password = sc.nextLine();

        return this.login(new CustomerLoginRequest(email, password));

    }

    public CustomerResponse create(CustomerRequest request){

        try {

            return service.create(request);

        } catch (Exception e){

            System.out.println(e.getMessage());
            return null;

        }

    }

    public Long login(CustomerLoginRequest request){

        try {

            return service.login(request);

        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

    }

    public CustomerResponse find(Long id){

        try {

            return service.findById(id);

        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

    }

    public void createAdminIfNotExists() {
        try {

            Customer existing = service.findByEmail("admin@gmail.com");
            if (existing != null) {
                return;
            }

            service.create(new CustomerRequest(
                    "Admin",
                    "admin@gmail.com",
                    "admin123456",
                    "000.000.000-00",
                    "(11) 12345-1234",
                    UserType.ADMIN
            ));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
