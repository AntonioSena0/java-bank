package org.example.repository;

import org.example.dto.AccountIdResponse;
import org.example.dto.AccountResponse;
import org.example.mapper.AccountMapper;
import org.example.mapper.CustomerMapper;
import org.example.model.Account;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.security.auth.login.AccountNotFoundException;
import java.util.*;

public class AccountRepositoryImpl implements AccountRepository{

    @Override
    public Account create(Account request) {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.persist(request);
            tx.commit();
            return request;

        } catch (Exception e){
            if(tx != null && tx.isActive()){
                tx.rollback();
            }

            throw new RuntimeException("Erro interno ao criar a conta");
        } finally {
            if(session != null && session.isOpen()){
                session.close();
            }
        }

    }

    @Override
    public AccountResponse find(Long key) throws AccountNotFoundException {

        try(Session session = HibernateUtil.getSessionFactory().openSession()){

            Account account = session.get(Account.class, key);

            if(account == null) throw new AccountNotFoundException("Credenciais inválidas");

            AccountMapper mapper = new AccountMapper(new CustomerMapper());

            return mapper.toAccountResponse(account);

        }

    }

    @Override
    public List<Account> findByIdCustomer(Long key) throws AccountNotFoundException {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.createQuery("""
                SELECT a FROM Account a
                JOIN FETCH a.customer c
                WHERE c.id = :customerId
                ORDER BY a.id ASC
                """, Account.class)
                    .setParameter("customerId", key)
                    .getResultList();

        }

    }


    @Override
    public List<Long> login(Long customer_id) throws AccountNotFoundException {

        try(Session session = HibernateUtil.getSessionFactory().openSession()){

            List<Long> accountIds = session.createQuery(
                    "SELECT a.id FROM Account a JOIN FETCH a.customer WHERE a.customer.id = :customerId ORDER BY a.id ASC", Long.class
            )
                    .setParameter("customerId", customer_id)
                    .getResultList();

            if(accountIds.isEmpty())  throw new AccountNotFoundException("Ainda não possui contas");

            return accountIds;

        }

    }

    @Override
    public List<Account> findAll() {

        try(Session session = HibernateUtil.getSessionFactory().openSession()){

            return session.createQuery(
                    "FROM Account", Account.class
            ).getResultList();

        }
    }

    public void delete(Long id) throws AccountNotFoundException{

        try(Session session = HibernateUtil.getSessionFactory().openSession()){

            session.beginTransaction();

            Account existAccount = session.find(Account.class, id);

            if(existAccount == null){
                throw new AccountNotFoundException("Conta inexistente");
            }

            session.remove(existAccount);

            session.getTransaction().commit();
            session.flush();

        }

    }

}
