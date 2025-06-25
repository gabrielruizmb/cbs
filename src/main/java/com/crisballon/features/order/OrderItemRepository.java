package com.crisballon.features.order;

import java.util.List;
import javax.persistence.EntityManager;
import com.crisballon.configs.CustomizerFactory;

/**
 * Repositório para gerenciar itens dos pedidos no banco de dados
 */
public class OrderItemRepository {
    
    // Salva um item do pedido no banco
    public void create(OrderItem item) {
        EntityManager em = CustomizerFactory.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(item);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    // Busca todos os itens de um pedido
    public List<OrderItem> getByOrderId(Long orderId) {
        EntityManager em = CustomizerFactory.getEntityManager();
        try {
            return em.createQuery("SELECT oi FROM OrderItem oi WHERE oi.orderId = :orderId", OrderItem.class)
                    .setParameter("orderId", orderId)
                    .getResultList();
        } finally {
            em.close();
        }
    }
    
    // Remove todos os itens de um pedido
    public void deleteByOrderId(Long orderId) {
        EntityManager em = CustomizerFactory.getEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM OrderItem oi WHERE oi.orderId = :orderId")
              .setParameter("orderId", orderId)
              .executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
