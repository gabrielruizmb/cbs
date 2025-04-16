package com.example.features.client;

import javax.persistence.EntityManager;
import java.util.*;

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

    public List<Client> findAll(){
        return em.createQuery("SELECT c FROM Client c", Client.class).getResultList();
    }
}
