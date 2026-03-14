package org.example.repository;

import org.example.model.Customer;

import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.LoginException;
import java.util.HashMap;
import java.util.UUID;

public class CustomerRepositoryImpl implements CustomerRepository {

    private final HashMap<UUID, Customer> customers = new HashMap<>();
    private final HashMap<String, Customer> customersEmail = new HashMap<>();

    @Override
    public Customer create(Customer customer) {
        customers.put(customer.getId(), customer);
        customersEmail.put(customer.getEmail(), customer);
        return customer;
    }

    @Override
    public Customer findByEmail(String email) throws AccountNotFoundException {

        Customer existCustomer = customersEmail.get(email);

        if(existCustomer == null){
            throw new AccountNotFoundException("Cliente não encontrado");
        }

        return existCustomer;

    }

    @Override
    public Customer find(UUID id) throws AccountNotFoundException {

        Customer existCustomer = customers.get(id);

        if(existCustomer == null) {
            throw new AccountNotFoundException();
        }

        return existCustomer;

    }

    @Override
    public UUID login(Customer customer) throws AccountNotFoundException, LoginException{

        Customer existCustomer = this.findByEmail(customer.getEmail());

        if(customer.getPassword().equals(existCustomer.getPassword())){

            return existCustomer.getId();

        }

        throw new LoginException("Senha ou email inválidos");

    }

}
