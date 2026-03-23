package org.example.repository;

import jakarta.persistence.EntityNotFoundException;
import org.example.dto.PixKeyResponse;
import org.example.model.Account;
import org.example.model.PixKey;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.management.openmbean.KeyAlreadyExistsException;
import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

public class PixKeyRepositoryImpl implements PixKeyRepository{

    @Override
    public PixKey create(PixKey request) {

        Session session = null;
        Transaction tx = null;

        try {

            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            Long accountId = request.getAccount().getId();

            Account account = session.get(Account.class, accountId);
            if (account == null) {
                throw new EntityNotFoundException("Conta não encontrada ao criar chave Pix: " + accountId);
            }

            PixKey pixKey = new PixKey();
            pixKey.setKeyValue(request.getKeyValue());
            pixKey.setType(request.getType());
            pixKey.setAccount(account);

            session.persist(pixKey);

            tx.commit();
            return pixKey;

        } catch (Exception e){

            if(tx != null && tx.isActive()){
                tx.rollback();
            }

            throw new RuntimeException("Erro interno ao criar chave pix");

        } finally {

            if(session != null && session.isOpen()){
                session.close();
            }

        }

    }

    @Override
    public List<PixKey> findByAccountId(Long id) throws ClassNotFoundException{

        try(Session session = HibernateUtil.getSessionFactory().openSession()){

            List<PixKey> pixKeys = session.createQuery(
                    "FROM PixKey pk JOIN pk.account a WHERE a.id = :id", PixKey.class
            )
                    .setParameter("id", id)
                    .getResultList();

            if(pixKeys.isEmpty()){
                throw new ClassNotFoundException("Você não possui chaves pix");
            }

            return pixKeys;

        }

    }

    @Override
    public Account findByPixKey(String key) throws AccountNotFoundException {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            PixKey pixKey = session.createQuery("""
                FROM PixKey pk WHERE pk.keyValue = :key
            """, PixKey.class)
                    .setParameter("key", key)
                    .uniqueResult();

            if (pixKey == null) {
                throw new AccountNotFoundException("Chave pix não encontrada");
            }

            return pixKey.getAccount();
        }

    }

    @Override
    public PixKey update(String newKey, Long id) throws ClassNotFoundException, KeyAlreadyExistsException {

        try(Session session = HibernateUtil.getSessionFactory().openSession()){

            session.beginTransaction();
            PixKey pixKey = session.find(PixKey.class, id);

            if(pixKey == null){
                throw new ClassNotFoundException("Chave pix inexistente para realizar a atualização");
            }

            PixKey keyVal = session.createQuery("""
                FROM PixKey pk WHERE pk.keyValue = :key
            """, PixKey.class)
                    .setParameter("key", newKey)
                    .uniqueResult();

            if(keyVal != null){
                throw new KeyAlreadyExistsException("Essa chave pix já existe");
            }

            pixKey.setKeyValue(newKey);

            session.getTransaction().commit();
            session.flush();

            return pixKey;

        }
    }

    @Override
    public void delete(Long id) throws ClassNotFoundException {

        try(Session session = HibernateUtil.getSessionFactory().openSession()){

            session.beginTransaction();

            PixKey pixKey = session.find(PixKey.class, id);

            if(pixKey == null){
                throw new ClassNotFoundException("Chave pix inexistente para realizar a exclusão");
            }

            session.remove(pixKey);

            session.getTransaction().commit();
            session.flush();

        }

    }
}
