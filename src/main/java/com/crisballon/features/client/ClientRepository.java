package com.crisballon.features.client;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import com.crisballon.configs.CustomizerFactory;

public class ClientRepository {

    // Cria um novo cliente no banco de dados
    public void create(Client client) {
        EntityManager em = CustomizerFactory.getEntityManager();
        try {
            em.getTransaction().begin(); // Inicia transação
            em.persist(client); // Salva o cliente
            em.flush(); // Força a gravação imediata
            em.getTransaction().commit(); // Confirma a transação
            System.out.println("Cliente salvo: " + client.getName());
        } catch (Exception e) {
            em.getTransaction().rollback(); // Desfaz em caso de erro
            System.out.println("Erro ao salvar cliente: " + e.getMessage());
            throw e;
        } finally {
            em.close(); // Sempre fecha a conexão
        }
    }

    // Busca um cliente específico pelo ID
    public Client getById(Long id) {
        EntityManager em = CustomizerFactory.getEntityManager();
        try {
            return em.find(Client.class, id); // Busca por chave primária
        } finally {
            em.close();
        }
    }

    // Retorna todos os clientes cadastrados
    public List<Client> getAll() {
        EntityManager em = CustomizerFactory.getEntityManager();
        try {
            // Consulta JPQL para buscar todos os clientes
            TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c", Client.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Atualiza os dados de um cliente existente
    public void update(Client client) {
        EntityManager em = CustomizerFactory.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(client); // Atualiza o cliente no banco
            em.flush(); // Força a gravação imediata
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Remove um cliente do banco de dados
    public void delete(Client client) {
        EntityManager em = CustomizerFactory.getEntityManager();
        try {
            em.getTransaction().begin();
            // Garante que o cliente está gerenciado pelo EntityManager
            Client managedClient = em.contains(client) ? client : em.merge(client);
            em.remove(managedClient); // Remove do banco
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
