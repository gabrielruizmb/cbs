package com.crisballon.features.product;

import java.util.List;
import javax.persistence.EntityManager;
import com.crisballon.configs.CustomizerFactory;

public class ProductRepository {
    
    // Cria um novo produto no banco de dados
    public void create(Product product) {
        EntityManager em = CustomizerFactory.getEntityManager();
        try {
            em.getTransaction().begin(); // Inicia transação
            em.persist(product); // Salva o produto
            em.flush(); // Força gravação imediata
            em.getTransaction().commit(); // Confirma transação
        } finally {
            em.close(); // Sempre fecha conexão
        }
    }

    // Atualiza um produto existente
    public void update(Product product) {
        EntityManager em = CustomizerFactory.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(product); // Atualiza o produto no banco
            em.flush();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Retorna todos os produtos cadastrados
    public List<Product> getAll() {
        EntityManager em = CustomizerFactory.getEntityManager();
        try {
            // Consulta JPQL: 'p' é alias para Product
            return em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
        } finally {
            em.close();
        }
    }

    // Busca um produto específico pelo ID
    public Product getById(Long id) {
        EntityManager em = CustomizerFactory.getEntityManager();
        try {
            return em.find(Product.class, id); // Busca por chave primária
        } finally {
            em.close();
        }
    }

    // Remove um produto do banco de dados
    public void delete(Product product) {
        EntityManager em = CustomizerFactory.getEntityManager();
        try {
            em.getTransaction().begin();
            // Busca o produto gerenciado pelo EntityManager
            Product managedProduct = em.find(Product.class, product.getId());
            if (managedProduct != null) {
                em.remove(managedProduct); // Remove do banco
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
