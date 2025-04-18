package com.example.features.request;

import javax.persistence.EntityManager;

import com.example.configs.CustomizerFactory;

public class RequestRepository {
    
    public void create(Request request) {
        EntityManager em = CustomizerFactory.getEntityManager();

        em.getTransaction().begin();
        em.persist(request);
        em.getTransaction().commit();
    }
}
