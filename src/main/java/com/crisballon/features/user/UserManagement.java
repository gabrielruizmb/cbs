package com.crisballon.features.user;

import java.time.LocalDateTime;

/**
 * Classe para gerenciar usuários do sistema
 * Controla login, logout e permissões
 */
public class UserManagement {
    
    private static UserRepository userRepository = new UserRepository();
    private static AuditLogRepository auditRepository = new AuditLogRepository();
    private static User currentUser = null;

    // Cria usuário admin padrão 
    public static void initializeDefaultAdmin() {
        try {
            User existingAdmin = userRepository.findByUsername("admin");
            if (existingAdmin != null) {
                System.out.println("Removendo usuário admin existente...");
                userRepository.delete(existingAdmin);
            }
            
            System.out.println("Criando usuário admin padrão...");
            User admin = new User("admin", "admin123", UserRole.ADMIN);
            userRepository.create(admin);
            auditRepository.create(new AuditLog("SYSTEM", "CREATE_USER", "Usuario admin padrao criado"));
            System.out.println("Usuário admin criado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao inicializar admin: " + e.getMessage());
        }
    }

    // Faz login do usuário
    public static User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            user.setLastLogin(LocalDateTime.now());
            userRepository.update(user);
            currentUser = user;
            auditRepository.create(new AuditLog(username, "LOGIN", "User logged in"));
            return user;
        }
        return null;
    }

    // Faz logout do usuário atual
    public static void logout() {
        if (currentUser != null) {
            auditRepository.create(new AuditLog(currentUser.getUsername(), "LOGOUT", "User logged out"));
            currentUser = null;
        }
    }

    // Retorna o usuário logado
    public static User getCurrentUser() {
        return currentUser;
    }

    // Verifica se o usuário atual é admin
    public static boolean isAdmin() {
        return currentUser != null && currentUser.getRole() == UserRole.ADMIN;
    }
}
