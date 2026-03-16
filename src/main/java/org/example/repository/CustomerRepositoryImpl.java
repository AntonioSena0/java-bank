package org.example.repository;

import org.example.dto.CustomerLoginRequest;
import org.example.model.Customer;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.LoginException;

public class CustomerRepositoryImpl implements CustomerRepository {

    @Override
    public Customer create(Customer request) {

        Session session = null;
        Transaction tx = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();

            tx = session.beginTransaction();
            session.persist(request);
            tx.commit();

            return request;

        } catch (Exception e){

            if(tx != null && tx.isActive()){
                tx.rollback();
            }

            throw new RuntimeException("Erro interno ao criar cliente");
        } finally {
            if( session != null && session.isOpen()) session.close();
        }

    }

    @Override
    public Customer findByEmail(String email) {

        try(Session session = HibernateUtil.getSessionFactory().openSession()){

            Customer customer = session.createQuery(
                    "FROM Customer WHERE email = :email", Customer.class
            )
                    .setParameter("email", email)
                    .uniqueResult();

            return customer;

        }

    }

    @Override
    public Customer find(Long id) throws AccountNotFoundException {

        try(Session session = HibernateUtil.getSessionFactory().openSession()){

            Customer existCustomer = session.find(Customer.class, id);

            if(existCustomer == null) throw new AccountNotFoundException("Credenciais inválidas");

            return existCustomer;

        }

    }

    @Override
    public Long login(CustomerLoginRequest request) throws LoginException{

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Customer customer = session.createQuery(
                            "FROM Customer WHERE LOWER(email) = LOWER(:email)", Customer.class)
                    .setParameter("email", request.email())
                    .uniqueResult();

            if (customer == null || !request.password().equals(customer.getPassword())) {
                throw new LoginException("Credenciais inválidas");
            }
            return customer.getId();
        }

    }

}
