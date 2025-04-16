package com.example.features.client;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class ClientRepository {
    private final EntityManager em;

    public ClientRepository(EntityManager em) {
        this.em = em;
    }

    // CREATE - Persiste novo cliente no banco
    public void create(Client client) {
        em.getTransaction().begin();
        em.persist(client);
        em.getTransaction().commit();
    }

    // READ - Busca cliente por ID
    public Client getById(Long id) {
        return em.find(Client.class, id); // Retorna null se não encontrar
    }

    // READ - Lista todos os clientes
    public List<Client> getAll() {
        return em.createQuery("SELECT c FROM Client c", Client.class)
                .getResultList();
    }

    // UPDATE - Atualiza cliente existente
    public void update(Client client) {
        em.getTransaction().begin();
        em.merge(client); // Atualiza a entidade no contexto de persistência
        em.getTransaction().commit();
    }

    // DELETE - Remove cliente do banco
    public void delete(Client client) {
        em.getTransaction().begin();
        em.remove(em.contains(client) ? client : em.merge(client));
        em.getTransaction().commit();
    }

    // CONSULTA COM LIKE - Busca por nome 
    public List<Client> findByNameLike(String name) {
        return em.createQuery(
            "SELECT c FROM Client c WHERE c.name LIKE :name", Client.class)
            .setParameter("name", "%" + name + "%")
            .getResultList();
    }

    // CONSULTA COM COUNT - Total de clientes 
    public Long countClients() {
        return em.createQuery(
            "SELECT COUNT(c) FROM Client c", Long.class)
            .getSingleResult();
    }
}
