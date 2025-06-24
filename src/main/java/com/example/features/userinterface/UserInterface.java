package com.example.features.userinterface;

import java.util.Scanner;

import com.example.features.client.ClientCrud;
import com.example.features.request.RequestCrud;

public class UserInterface {

    public static Scanner scanner = new Scanner(System.in);
    
    private static boolean loginWithAttempts() {
        System.out.print("\n==============================");
        System.out.print("\n--- Sistema da Cris Ballon ---");
        System.out.print("\n~~~~~~~ Autenticação ~~~~~~~\n");

        int attempts = 0;
        int maxAttempts = 3;
        while (attempts < maxAttempts) {
            System.out.print("\nUsuário: ");
            String username = scanner.nextLine();
            
            System.out.print("Senha: ");
            String password = scanner.nextLine();

            com.example.features.user.User user = com.example.features.user.UserManagement.login(username, password);
            if (user != null) {
                System.out.printf("\nBem-vindo, %s! (%s)\n", user.getUsername(), user.getRole().getDisplayName());
                return true;
            }

            attempts++;
            System.out.println("\nCredenciais inválidas! Tentativas restantes: " + (maxAttempts - attempts));
        }

        System.out.println("\nNúmero máximo de tentativas excedido!");
        return false;
    }
    
    public static int getValidIntInput() {
        while (true) {
            try {
                int input = scanner.nextInt();
                scanner.nextLine(); // Limpar buffer
                return input;
            } catch (Exception e) {
                System.out.print("Entrada inválida! Digite apenas números: ");
                scanner.nextLine(); // Limpar entrada inválida
            }
        }
    }
    
    public static void mainMenu() {
        com.example.features.user.UserManagement.initializeDefaultAdmin();
        
        if (!loginWithAttempts()) {
            System.out.println("Acesso negado. Encerrando sistema...");
            System.exit(0);
        }
        
        while (true) {
            com.example.features.user.User currentUser = com.example.features.user.UserManagement.getCurrentUser();
            System.out.print("\n==============================");
            System.out.print("\n--- Sistema da Cris Ballon ---");
            System.out.printf("\n~~~ Usuário: %s (%s) ~~~\n\n", currentUser.getUsername(), currentUser.getRole().getDisplayName());
            System.out.print("1. Clientes\n2. Pedidos\n3. Vendas\n");
            if (com.example.features.user.UserManagement.isAdmin()) {
                System.out.print("4. Gerenciar Usuários\n5. Trocar Usuário\n6. Manual do usuário\n7. Sair\n\n");
            } else {
                System.out.print("4. Trocar Usuário\n5. Manual do usuário\n6. Sair\n\n");
            }
            System.out.print("Escolha uma opção: ");

            int choice = getValidIntInput();

            if (com.example.features.user.UserManagement.isAdmin()) {
                switch (choice) {
                    case 1: clientMenu(); break;
                    case 2: requestsMenu(); break;
                    case 3: com.example.features.sales.SalesCrud.salesMenu(); break;
                    case 4: com.example.features.user.UserManagement.adminUserMenu(); break;
                    case 5: com.example.features.user.UserManagement.switchUser(); break;
                    case 6: userManual(); break;
                    case 7: 
                        com.example.features.user.UserManagement.logout();
                        System.out.println("\nSistema encerrado. Até logo!");
                        System.exit(0);
                    default:
                        System.out.print("\nOpção inválida!\n");
                        System.out.print("\nPressione Enter para continuar...");
                        scanner.nextLine();
                }
            } else {
                switch (choice) {
                    case 1: clientMenu(); break;
                    case 2: requestsMenu(); break;
                    case 3: com.example.features.sales.SalesCrud.salesMenu(); break;
                    case 4: com.example.features.user.UserManagement.switchUser(); break;
                    case 5: userManual(); break;
                    case 6: 
                        com.example.features.user.UserManagement.logout();
                        System.out.println("\nSistema encerrado. Até logo!");
                        System.exit(0);
                    default:
                        System.out.print("\nOpção inválida!\n");
                        System.out.print("\nPressione Enter para continuar...");
                        scanner.nextLine();
                }
            }
        }
    }



    public static void clientMenu() {
        int choice = 0;
        
        while (choice != 5) {
            System.out.print("\n==============================\n");
            System.out.print("--- Sistema da Cris Ballon --- \n\n");
            System.out.print("~~~ Menu clientes ~~~ \n\n");
    
            System.out.print(" 1.Adicionar cliente \n 2.Editar cliente \n");
            System.out.print(" 3.Remover cliente \n 4.Listar clientes \n");
            System.out.print(" 5.Voltar \n\n");

            System.out.print("Escolha uma opção: ");
            choice = getValidIntInput();

            switch (choice) {
                case 1: ClientCrud.create(); break;
                case 2: ClientCrud.update(); break;
                case 3: ClientCrud.delete(); break;
                case 4: ClientCrud.getAll(); break;
                case 5: break;
                default:
                    System.out.print("\nOpção inválida!\n");
                    scanner.nextLine();
                    System.out.print("\nPressione Enter para continuar . . .");
                    scanner.nextLine();
            }
        }
    }

    public static void requestsMenu() {
        int choice = 0;
        
        while (choice != 5) {
            System.out.print("\n==============================\n");
            System.out.print("--- Sistema da Cris Ballon --- \n\n");
            System.out.print("~~~ Menu de pedidos ~~~ \n\n");
    
            System.out.print(" 1.Novo pedido \n 2.Editar pedido \n");
            System.out.print(" 3.Excluir pedido \n 4.Ver pedidos \n");
            System.out.print(" 5.Voltar \n\n");

            System.out.print("Escolha uma opção: ");
            choice = getValidIntInput();

            switch (choice) {
                case 1: RequestCrud.create(); break;
                case 2: RequestCrud.update(); break;
                case 3: RequestCrud.delete(); break;
                case 4: RequestCrud.getAll(); break;
                case 5: break;
                default:
                    System.out.print("\nOpção inválida!\n");
                    scanner.nextLine();
                    System.out.print("\nPressione Enter para continuar . . .");
                    scanner.nextLine();
            }
        }
    }

    public static void userManual() {
        System.out.print("\n==============================\n");
        System.out.print("--- Sistema da Cris Ballon --- \n\n");
        System.out.print("~~~ Manual do usuário ~~~ \n\n");
        System.out.print("1) Navegue pelo sistema utilizando o teclado\n");

        scanner.nextLine();
        System.out.print("\nPressione Enter para voltar . . .");
        scanner.nextLine();
    }
    

}