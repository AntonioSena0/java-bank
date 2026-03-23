package org.example.repository;

import org.example.dto.CustomerLoginRequest;
import org.example.model.Customer;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.management.openmbean.KeyAlreadyExistsException;
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
    public Customer findByName(String name) {

        try(Session session = HibernateUtil.getSessionFactory().openSession()){

            return session.createQuery(
                            "FROM Customer WHERE name = :name", Customer.class
                    )
                    .setParameter("name", name)
                    .uniqueResult();
        }

    }

    @Override
    public Customer findByEmail(String email) {

        try(Session session = HibernateUtil.getSessionFactory().openSession()){

            return session.createQuery(
                    "FROM Customer WHERE email = :email", Customer.class
            )
                    .setParameter("email", email)
                    .uniqueResult();

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

    @Override
    public Customer update(Customer request, Long id) throws AccountNotFoundException {

        try(Session session = HibernateUtil.getSessionFactory().openSession()){

            session.beginTransaction();

            Customer customer = session.find(Customer.class, id);

            if(customer == null){
                throw new AccountNotFoundException("Cliente inexistente para realizar alteração");
            }

            if(request.getName() != null) {

                if(findByName(request.getName()) != null){
                    throw new KeyAlreadyExistsException("Credenciais inválidas para realizar a alteração");
                }

                customer.setName(request.getName());
            }


            if(request.getEmail() != null){

                if(findByEmail(request.getEmail()) != null){
                    throw new KeyAlreadyExistsException("Credenciais inválidas para realizar a alteração");
                }

                customer.setEmail(request.getEmail());
            }

            session.getTransaction().commit();
            session.flush();

            return customer;

        }

    }

    @Override
    public void delete(Long id) throws AccountNotFoundException {

        try(Session session = HibernateUtil.getSessionFactory().openSession()){

            session.beginTransaction();

            Customer existCustomer = session.find(Customer.class, id);

            if(existCustomer == null){
                throw new AccountNotFoundException("Cliente inexistente para realizar a exclusão");
            }

            session.remove(existCustomer);

            session.getTransaction().commit();
            session.flush();

        }

    }
}
