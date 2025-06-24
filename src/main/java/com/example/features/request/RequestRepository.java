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

    public void delete(Request request) {
        EntityManager em = CustomizerFactory.getEntityManager();

        em.getTransaction().begin();
        em.remove(request);
        em.getTransaction().commit();
    }

    public List<Request> getPendingRequests() {
        EntityManager em = CustomizerFactory.getEntityManager();

        return em.createQuery("SELECT r FROM Request r WHERE r.confirmed = false OR r.confirmed IS NULL", 
                            Request.class).getResultList();
    }

    public List<Request> getConfirmedSales() {
        EntityManager em = CustomizerFactory.getEntityManager();

        return em.createQuery("SELECT r FROM Request r WHERE r.confirmed = true", 
                            Request.class).getResultList();
    }
}
