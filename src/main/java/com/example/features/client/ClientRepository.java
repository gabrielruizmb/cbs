package com.example.features.client;

import java.util.List;

import javax.persistence.EntityManager;

public class ClientRepository {
    private EntityManager em;

    public ClientRepository(EntityManager em) {
        this.em = em;
    }

    public void create(Client client) {
        em.getTransaction().begin();
        em.persist(client);
        em.getTransaction().commit();
    }

    public List<Client> getAll() {
        return em.createQuery("SELECT c FROM Client c", Client.class)
                 .getResultList();
    }

    public Client getById(Long id) {
        return em.find(Client.class, id);
    }

    public void update(Client client) {
        em.getTransaction().begin();
        em.merge(client);
        em.getTransaction().commit();
    }

    public void delete(Client client) {
        em.getTransaction().begin();
        em.remove(client);
        em.getTransaction().commit();
    }
}
