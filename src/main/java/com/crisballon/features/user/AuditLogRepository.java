package com.crisballon.features.user;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import com.crisballon.configs.CustomizerFactory;

public class AuditLogRepository {

    public void create(AuditLog auditLog) {
        EntityManager em = CustomizerFactory.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(auditLog);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<AuditLog> getAll() {
        EntityManager em = CustomizerFactory.getEntityManager();
        try {
            TypedQuery<AuditLog> query = em.createQuery("SELECT a FROM AuditLog a ORDER BY a.timestamp DESC", AuditLog.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<AuditLog> getByUsername(String username) {
        EntityManager em = CustomizerFactory.getEntityManager();
        try {
            TypedQuery<AuditLog> query = em.createQuery("SELECT a FROM AuditLog a WHERE a.username = :username ORDER BY a.timestamp DESC", AuditLog.class);
            query.setParameter("username", username);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
