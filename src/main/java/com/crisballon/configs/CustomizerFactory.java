package com.crisballon.configs;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

// Esta classe tem um método que cria um objeto que indica onde está 
// o arquivo que conecta a aplicação com banco de dados.

public class CustomizerFactory {
    
    private static final EntityManagerFactory emf;

    static {
        SessionFactory sessionFactory = new Configuration()
            .configure()
            .buildSessionFactory();

        emf = sessionFactory.unwrap(EntityManagerFactory.class);
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void close() {
        emf.close();
    }
}
