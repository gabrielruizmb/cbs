package com.crisballon.features.order;

import java.util.List;

import javax.persistence.EntityManager;

import com.crisballon.configs.CustomizerFactory;

public class OrderRepository {
    
    // Cria um novo pedido no banco de dados
    public void create(Order order) {
        EntityManager em = CustomizerFactory.getEntityManager();
        try {
            em.getTransaction().begin(); // Inicia transação
            em.persist(order); // Salva o pedido
            em.flush(); // Força gravação imediata
            em.getTransaction().commit(); // Confirma transação
        } catch (Exception e) {
            em.getTransaction().rollback(); // Desfaz em caso de erro
            throw e;
        } finally {
            em.close(); // Sempre fecha conexão
        }
    }

    // Atualiza um pedido existente
    public void update(Order order) {
        EntityManager em = CustomizerFactory.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(order); // Atualiza o pedido no banco
            em.flush();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // Retorna todos os pedidos cadastrados
    public List<Order> getAll() {
        EntityManager em = CustomizerFactory.getEntityManager();
        try {
            // Consulta JPQL: 'o' é alias para Order
            return em.createQuery("SELECT o FROM Order o", 
                                Order.class).getResultList();
        } finally {
            em.close();
        }
    }

    // Busca um pedido específico pelo ID
    public Order getById(Long id) {
        EntityManager em = CustomizerFactory.getEntityManager();
        try {
            return em.find(Order.class, id); // Busca por chave primária
        } finally {
            em.close();
        }
    }

    // Remove um pedido do banco de dados
    public void delete(Order order) {
        EntityManager em = CustomizerFactory.getEntityManager();
        try {
            em.getTransaction().begin();
            // Busca o pedido gerenciado pelo EntityManager
            Order managedOrder = em.find(Order.class, order.getId());
            if (managedOrder != null) {
                em.remove(managedOrder); // Remove do banco
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Busca apenas pedidos pendentes (não confirmados)
    public List<Order> getPendingOrders() {
        EntityManager em = CustomizerFactory.getEntityManager();
        try {
            // Pedidos com confirmed = false ou NULL (ainda não definido)
            return em.createQuery("SELECT o FROM Order o WHERE o.confirmed = false OR o.confirmed IS NULL", 
                                Order.class).getResultList();
        } finally {
            em.close();
        }
    }

    // Busca apenas vendas confirmadas
    public List<Order> getConfirmedSales() {
        EntityManager em = CustomizerFactory.getEntityManager();
        try {
            // Apenas pedidos com confirmed = true
            return em.createQuery("SELECT o FROM Order o WHERE o.confirmed = true", 
                                Order.class).getResultList();
        } finally {
            em.close();
        }
    }
}
