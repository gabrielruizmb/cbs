package com.crisballon.features.user;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import com.crisballon.configs.CustomizerFactory;

public class UserRepository {

    // Cria um novo usuário no banco de dados
    public void create(User user) {
        EntityManager em = CustomizerFactory.getEntityManager();
        try {
            em.getTransaction().begin(); // Inicia transação
            em.persist(user); // Salva o usuário
            em.getTransaction().commit(); // Confirma transação
        } finally {
            em.close(); // Sempre fecha conexão
        }
    }

    // Busca um usuário pelo nome de usuário (login)
    public User findByUsername(String username) {
        EntityManager em = CustomizerFactory.getEntityManager();
        try {
            // Consulta JPQL com parâmetro nomeado :username
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
            query.setParameter("username", username); // Define o valor do parâmetro
            List<User> users = query.getResultList();
            return users.isEmpty() ? null : users.get(0); // Retorna null se não encontrar
        } finally {
            em.close();
        }
    }

    // Busca um usuário pelo ID
    public User getById(Long id) {
        EntityManager em = CustomizerFactory.getEntityManager();
        try {
            return em.find(User.class, id); // Busca por chave primária
        } finally {
            em.close();
        }
    }

    // Retorna todos os usuários cadastrados
    public List<User> getAll() {
        EntityManager em = CustomizerFactory.getEntityManager();
        try {
            // Consulta JPQL para buscar todos os usuários
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Atualiza os dados de um usuário existente
    public void update(User user) {
        EntityManager em = CustomizerFactory.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(user); // Atualiza o usuário no banco
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Remove um usuário do banco de dados
    public void delete(User user) {
        EntityManager em = CustomizerFactory.getEntityManager();
        try {
            em.getTransaction().begin();
            // Garante que o usuário está gerenciado pelo EntityManager
            User managedUser = em.contains(user) ? user : em.merge(user);
            em.remove(managedUser); // Remove do banco
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
