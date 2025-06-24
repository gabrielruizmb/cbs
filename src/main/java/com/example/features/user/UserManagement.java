package com.example.features.user;

import java.time.LocalDateTime;
import java.util.List;
import com.example.features.userinterface.UserInterface;

public class UserManagement {
    
    private static UserRepository userRepository = new UserRepository();
    private static AuditLogRepository auditRepository = new AuditLogRepository();
    private static User currentUser = null;

    public static void initializeDefaultAdmin() {
        try {
            User existingAdmin = userRepository.findByUsername("admin");
            if (existingAdmin != null) {
                // Remover usuário admin existente
                System.out.println("Removendo usuário admin existente...");
                userRepository.delete(existingAdmin);
            }
            
            // Criar novo usuário admin
            System.out.println("Criando usuário admin padrão...");
            User admin = new User("admin", "admin123", UserRole.ADMIN);
            userRepository.create(admin);
            auditRepository.create(new AuditLog("SYSTEM", "CREATE_USER", "Usuario admin padrao criado"));
            System.out.println("Usuário admin criado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao inicializar admin: " + e.getMessage());
            e.printStackTrace();
        }
    }

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

    public static void logout() {
        if (currentUser != null) {
            auditRepository.create(new AuditLog(currentUser.getUsername(), "LOGOUT", "User logged out"));
            currentUser = null;
        }
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static boolean isAdmin() {
        return currentUser != null && currentUser.getRole() == UserRole.ADMIN;
    }

    public static void adminUserMenu() {
        if (!isAdmin()) {
            System.out.print("Acesso negado! Apenas administradores podem acessar este menu.\n");
            return;
        }

        int choice = 0;
        while (choice != 6) {
            System.out.print("\n==============================\n");
            System.out.print("--- Sistema da Cris Ballon ---\n");
            System.out.print("~~~~ Gerenciamento de Usuários ~~~~\n\n");
            System.out.print("1. Adicionar novo usuário\n");
            System.out.print("2. Listar usuários\n");
            System.out.print("3. Alterar permissões\n");
            System.out.print("4. Excluir usuário\n");
            System.out.print("5. Ver logs de auditoria\n");
            System.out.print("6. Voltar\n\n");
            System.out.print("Escolha uma opção: ");
            
            choice = UserInterface.getValidIntInput();

            switch (choice) {
                case 1: createUser(); break;
                case 2: listUsers(); break;
                case 3: changeUserRole(); break;
                case 4: deleteUser(); break;
                case 5: viewAuditLogs(); break;
                case 6: break;
                default:
                    System.out.print("\nOpção inválida!\n");
            }
            
            if (choice != 6) {
                System.out.print("\nPressione Enter para continuar...");
                UserInterface.scanner.nextLine();
            }
        }
    }

    private static void createUser() {
        System.out.print("\nNovo usuário\n------------\n");
        System.out.print("Nome de usuário: ");
        String username = UserInterface.scanner.nextLine();
        
        if (userRepository.findByUsername(username) != null) {
            System.out.print("Usuário já existe!\n");
            return;
        }
        
        System.out.print("Senha: ");
        String password = UserInterface.scanner.nextLine();
        
        System.out.print("Tipo de usuário:\n1. Admin\n2. Usuário Padrão\nEscolha: ");
        int roleChoice = UserInterface.getValidIntInput();
        
        UserRole role = (roleChoice == 1) ? UserRole.ADMIN : UserRole.USER;
        
        User newUser = new User(username, password, role);
        userRepository.create(newUser);
        
        auditRepository.create(new AuditLog(currentUser.getUsername(), "CREATE_USER", 
            "Created user: " + username + " with role: " + role.getDisplayName()));
        
        System.out.print("Usuário cadastrado com sucesso!\n");
    }

    private static void listUsers() {
        System.out.print("\nUsuários cadastrados:\n");
        System.out.print("=====================\n");
        
        List<User> users = userRepository.getAll();
        for (User user : users) {
            System.out.printf("ID: %d | Usuário: %s | Tipo: %s | Criado: %s\n", 
                user.getId(), user.getUsername(), user.getRole().getDisplayName(), 
                user.getCreatedAt().toString().substring(0, 19));
        }
    }

    private static void changeUserRole() {
        System.out.print("\nAlterar permissões de usuário\n");
        System.out.print("Nome do usuário: ");
        String username = UserInterface.scanner.nextLine();
        
        User user = userRepository.findByUsername(username);
        if (user == null) {
            System.out.print("Usuário não encontrado!\n");
            return;
        }
        
        System.out.printf("Usuário atual: %s (%s)\n", user.getUsername(), user.getRole().getDisplayName());
        System.out.print("Nova permissão:\n1. Admin\n2. Usuário Padrão\nEscolha: ");
        int roleChoice = UserInterface.getValidIntInput();
        
        UserRole oldRole = user.getRole();
        UserRole newRole = (roleChoice == 1) ? UserRole.ADMIN : UserRole.USER;
        
        user.setRole(newRole);
        userRepository.update(user);
        
        auditRepository.create(new AuditLog(currentUser.getUsername(), "CHANGE_ROLE", 
            "Changed role of " + username + " from " + oldRole.getDisplayName() + " to " + newRole.getDisplayName()));
        
        System.out.print("Permissões alteradas com sucesso!\n");
    }

    private static void deleteUser() {
        System.out.print("\nExcluir usuário\n");
        System.out.print("Nome do usuário: ");
        String username = UserInterface.scanner.nextLine();
        
        if (username.equals("admin")) {
            System.out.print("Não é possível excluir o usuário admin!\n");
            return;
        }
        
        if (username.equals(currentUser.getUsername())) {
            System.out.print("Não é possível excluir o próprio usuário!\n");
            return;
        }
        
        User user = userRepository.findByUsername(username);
        if (user == null) {
            System.out.print("Usuário não encontrado!\n");
            return;
        }
        
        System.out.printf("Tem certeza que deseja excluir o usuário '%s' (%s)? (s/n): ", 
            user.getUsername(), user.getRole().getDisplayName());
        String confirm = UserInterface.scanner.nextLine();
        
        if (confirm.toLowerCase().equals("s")) {
            userRepository.delete(user);
            auditRepository.create(new AuditLog(currentUser.getUsername(), "DELETE_USER", 
                "Deleted user: " + username + " with role: " + user.getRole().getDisplayName()));
            System.out.print("Usuário excluído com sucesso!\n");
        } else {
            System.out.print("Operação cancelada.\n");
        }
    }

    private static void viewAuditLogs() {
        System.out.print("\nLogs de Auditoria\n");
        System.out.print("=================\n");
        
        List<AuditLog> logs = auditRepository.getAll();
        for (AuditLog log : logs) {
            System.out.printf("[%s] %s - %s: %s\n", 
                log.getTimestamp().toString().substring(0, 19),
                log.getUsername(), log.getAction(), log.getDetails());
        }
    }

    public static void switchUser() {
        System.out.print("\n==============================\n");
        System.out.print("--- Trocar de Usuário ---\n\n");
        System.out.print("Usuário: ");
        String username = UserInterface.scanner.nextLine();
        
        System.out.print("Senha: ");
        String password = UserInterface.scanner.nextLine();

        User user = login(username, password);
        if (user != null) {
            System.out.printf("\nBem-vindo, %s! (%s)\n", user.getUsername(), user.getRole().getDisplayName());
        } else {
            System.out.print("\nCredenciais inválidas!\n");
        }
        
        System.out.print("\nPressione Enter para continuar...");
        UserInterface.scanner.nextLine();
    }


}