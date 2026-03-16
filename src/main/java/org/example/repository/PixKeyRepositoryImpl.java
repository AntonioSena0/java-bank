package org.example.repository;

import jakarta.persistence.EntityNotFoundException;
import org.example.model.Account;
import org.example.model.PixKey;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.security.auth.login.AccountNotFoundException;

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

            account.addPixKey(pixKey);

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

}
