package org.example.repository;

import org.example.dto.TransactionResponse;
import org.example.enums.TransactionType;
import org.example.mapper.TransactionMapper;
import org.example.model.Account;
import org.example.model.Transaction;
import org.example.util.HibernateUtil;
import org.hibernate.Session;

import javax.naming.InsufficientResourcesException;
import javax.security.auth.login.AccountNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepositoryImpl implements TransactionRepository{

    @Override
    public void deposit(Long id, double value) throws AccountNotFoundException{

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {

            Account account = session.find(Account.class, id);

            if (account == null) {
                throw new AccountNotFoundException("Conta não encontrada: " + id);
            }
            if (value <= 0) {
                throw new IllegalArgumentException("Valor inválido: " + value);
            }

            session.beginTransaction();
            account.deposit(value, TransactionType.DEPOSIT);
            Transaction transaction = new Transaction(TransactionType.DEPOSIT, value, account);
            session.persist(transaction);
            session.getTransaction().commit();

        }

    }

    @Override
    public void withdraw(Long id, double value) throws InsufficientResourcesException, AccountNotFoundException{

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {

            Account account = session.find(Account.class, id);

            if (account == null) {
                throw new AccountNotFoundException("Conta não encontrada: " + id);
            }
            if (value <= 0) {
                throw new IllegalArgumentException("Valor inválido: " + value);
            }

            if(value > account.getBalance()){
                throw new InsufficientResourcesException();
            }

            session.beginTransaction();
            account.withdraw(   value, TransactionType.WITHDRAW);
            Transaction transaction = new Transaction(TransactionType.WITHDRAW, value, account);
            session.persist(transaction);
            session.getTransaction().commit();

        }

    }

    @Override
    public void transfer(Long id, Long to_id, double value) throws InsufficientResourcesException, AccountNotFoundException {

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {

            Account from_account = session.find(Account.class, id);
            Account to_account = session.find(Account.class, to_id);

            if (from_account == null) throw new AccountNotFoundException("Conta origem não encontrada");
            if (to_account == null) throw new AccountNotFoundException("Conta destino não encontrada");

            if(value <= 0){
                throw new IllegalArgumentException("Valor inválido");
            }
            if(value > from_account.getBalance()){
                throw new InsufficientResourcesException("Saldo insuficiente");
            }
            if(from_account.equals(to_account)){
                throw new IllegalArgumentException("Você não pode transferir para a mesma conta");
            }

            session.beginTransaction();
            from_account.withdraw(value, TransactionType.TRANSFER);
            to_account.deposit(value, TransactionType.TRANSFER);
            Transaction transaction = new Transaction(TransactionType.TRANSFER, value, from_account, to_account);
            session.persist(transaction);
            session.getTransaction().commit();

        }

    }

    @Override
    public TransactionResponse findTransaction(Long id) throws ClassNotFoundException {

        try(Session session = HibernateUtil.getSessionFactory().openSession()){

            Transaction transaction = session.find(Transaction.class, id);

            if(transaction == null){
                throw new ClassNotFoundException();
            }

            TransactionMapper mapper = new TransactionMapper();

            return mapper.toTransactionResponse(transaction);

        }

    }

    @Override
    public List<TransactionResponse> findAll() {

        try(Session session = HibernateUtil.getSessionFactory().openSession()){

            List<Transaction> txs = session.createQuery(
                    "FROM Transaction", Transaction.class
            )
                    .getResultList();

            TransactionMapper mapper = new TransactionMapper();
            List<TransactionResponse> responses = new ArrayList<>();
            txs.forEach(t -> responses.add(mapper.toTransactionResponse(t)));

            return responses;

        }

    }


    @Override
    public List<TransactionResponse> listTransactionsByAccountId(Long id) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Transaction> txs = session.createQuery("""
            SELECT t FROM Transaction t
            JOIN FETCH t.from_account fa
            LEFT JOIN FETCH t.to_account ta
            WHERE fa.id = :accountId OR ta.id = :accountId
            ORDER BY t.createdAt DESC
            """, Transaction.class)
                    .setParameter("accountId", id)
                    .getResultList();

            TransactionMapper mapper = new TransactionMapper();
            List<TransactionResponse> responses = new ArrayList<>();
            txs.forEach(t -> responses.add(mapper.toTransactionResponse(t)));

            return responses;
        }

    }

}
