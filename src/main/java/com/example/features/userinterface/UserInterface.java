package com.example.features.userinterface;

import java.util.Scanner;

import com.example.features.client.ClientCrud;

public class UserInterface {

    // Scanner que pode ser usado em toda a
    // aplicação para ler o teclado do usuário.
    public static Scanner scanner = new Scanner(System.in);
    
    public static void mainMenu() {
        
        while (true) {
            System.out.print("\n==============================\n");
            System.out.print("--- Sistema da Cris Ballon --- \n\n");
            System.out.print("~~~ Menu principal ~~~ \n\n");
    
            System.out.print(" 1.Clientes \n 2.Pedidos \n");
            System.out.print(" 3.Manual do usuário \n 4.Sair \n\n");
            System.out.print("Escolha uma opção: ");

            switch (scanner.nextInt()) {
                case 1:                  
                    clientMenu();
                    break;

                case 2:
                    break;

                case 3:
                    userManual();
                    break;
                
                case 4:
                    // Encerra o programa com código 0(sem problemas).
                    System.exit(0); 
            
                default:
                    System.out.print("\nOpção inválida!\n");

                    // Limpa o buffer do teclado para 
                    // poder ler a próxima linha.
                    scanner.nextLine(); 

                    System.out.print("\nPressione Enter para continuar . . .");
                    
                    // Espera o usuário pressionar "Enter" para continuar.
                    scanner.nextLine(); 
                    break;
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
            choice = scanner.nextInt();

            switch (choice) {
                case 1:    
                    ClientCrud.createClient();              
                    break;

                case 2:
                    break;

                case 3:
                    break;
                
                case 4:
                    ClientCrud.getAll();
                    break;

                case 5:
                    break;

                default:
                    System.out.print("\nOpção inválida!\n");
                    scanner.nextLine();
                    System.out.print("\nPressione Enter para continuar . . .");
                    scanner.nextLine();
                    break;
            }
            
        }
    }

    public static void userManual() {
        System.out.print("\n==============================\n");
        System.out.print("--- Sistema da Cris Ballon --- \n\n");
        System.out.print("~~~ Manual do usuário ~~~ \n\n");
        System.out.print("1)Navegue pelo sistema utilizando o teclado. \n");

        scanner.nextLine();
        System.out.print("\nPressione Enter para voltar . . .");
        scanner.nextLine();
    }
}
