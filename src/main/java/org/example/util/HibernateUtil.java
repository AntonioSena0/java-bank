package org.example.util;

import io.github.cdimascio.dotenv.Dotenv;
import org.example.model.Account;
import org.example.model.Customer;
import org.example.model.PixKey;
import org.example.model.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.InputStream;
import java.util.Properties;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Properties props = new Properties();

            try (InputStream is = HibernateUtil.class.getClassLoader()
                    .getResourceAsStream("hibernate.properties")) {
                if (is == null) throw new RuntimeException("hibernate.properties não encontrado!");
                props.load(is);
            } catch (Exception e) {
                e.printStackTrace();
            }

            overrideWithEnv(props);

            Configuration cfg = new Configuration().setProperties(props)
                    .addAnnotatedClass(Customer.class)
                    .addAnnotatedClass(Account.class)
                    .addAnnotatedClass(Transaction.class)
                    .addAnnotatedClass(PixKey.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(cfg.getProperties()).build();

            sessionFactory = cfg.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }

    private static void overrideWithEnv(Properties props) {
        Dotenv dotenv = Dotenv.configure().directory("./").load();

        String name = dotenv.get("DB_NAME");
        if (name != null){
            String url = props.getProperty("hibernate.connection.url");
            props.setProperty("hibernate.connection.url", url.replace("${DB_NAME}", name));
        }
        String user = dotenv.get("DB_USER");
        if (user != null) props.setProperty("hibernate.connection.username", user);
        String password = dotenv.get("DB_PASSWORD");
        if (password != null) props.setProperty("hibernate.connection.password", password);

    }
}
