package com.example.features.request;

import java.util.List;

import javax.persistence.EntityManager;

import com.example.configs.CustomizerFactory;

public class RequestRepository {
    
    public void create(Request request) {
        EntityManager em = CustomizerFactory.getEntityManager();

        em.getTransaction().begin();
        em.merge(request);
        em.getTransaction().commit();
    }

    public void update(Request request) {
        EntityManager em = CustomizerFactory.getEntityManager();

        em.getTransaction().begin();
        em.merge(request);
        em.getTransaction().commit();
    }

    public List<Request> getAll() {
        EntityManager em = CustomizerFactory.getEntityManager();

        return em.createQuery("SELECT request FROM Request request", 
                            Request.class).getResultList();
    }

    public Request getById(Long id) {
        EntityManager em = CustomizerFactory.getEntityManager();

        return em.find(Request.class, id);
    }
}
