package org.example.controller;

import org.example.model.Customer;
import org.example.service.Costumer.CustomerService;

import java.util.Scanner;
import java.util.UUID;

public class CostumerController {

    private final CustomerService service;

    public CostumerController(CustomerService customerService) {
        this.service = customerService;
    }

    public UUID showMenuRegister(){

        Scanner sc = new Scanner(System.in);
        System.out.println("Insira o seu nome");
        String name = sc.nextLine();
        System.out.println("Insira seu email");
        String email = sc.nextLine();
        System.out.println("Insira sua senha");
        String password = sc.nextLine();
        System.out.println("Insira seu CPF");
        String document = sc.nextLine();

        Customer newCustomer = this.create(new Customer(name, email, password, document));

        return newCustomer.getId();

    }

    public UUID showMenuLogin(){

        Scanner sc = new Scanner(System.in);
        System.out.println("Insira seu email");
        String email = sc.nextLine();
        System.out.println("Insira sua senha");
        String password = sc.nextLine();

        return this.login(new Customer(email, password));

    }

    public Customer create(Customer costumer){

        try {

            return service.create(costumer);

        } catch (Exception e){

            System.out.println(e.getMessage());
            return null;

        }

    }

    public UUID login(Customer customer){

        try {

            return service.login(customer);

        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

    }

}
