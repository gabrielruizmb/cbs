package com.example.features.userinterface;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.example.features.client.ClientCrud;
import com.example.features.request.RequestCrud;

public class UserInterface {

    // Scanner que pode ser usado em toda a
    // aplicação para ler o teclado do usuário.
    public static Scanner scanner = new Scanner(System.in);

    public static void guiNewClient(JFrame window) {
        System.out.print("~~~ Teste ~~~ \n\n");
    }
    
    public static void mainMenu(JFrame window) {
        
        while (true) {

            window.getContentPane().setBackground(Color.MAGENTA);

            // JLabel appLogo = new JLabel("--- Sistema da Cris Ballon ---");
            // appLogo.setHorizontalAlignment(JLabel.CENTER);

            // window.add(appLogo);

            JMenuBar menuBar = new JMenuBar();

            JLabel appLogo = new JLabel("Sistema da Cris Ballon         ");
            menuBar.add(appLogo);

            JMenu clientsMenu = new JMenu("Clientes");
            JMenuItem clientsMenuNewClient = new JMenuItem("Novo cliente +");
            clientsMenuNewClient.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    guiNewClient(window);
                }
                
            });

            JMenuItem clientsMenuEditClient = new JMenuItem("Editar cliente");
            JMenuItem clientsMenuRemoveClient = new JMenuItem("Remover cliente");
            JMenuItem clientsMenuListClients = new JMenuItem("Listar clientes");

            clientsMenu.add(clientsMenuNewClient);
            clientsMenu.add(clientsMenuEditClient);
            clientsMenu.add(clientsMenuRemoveClient);
            clientsMenu.add(clientsMenuListClients);

            JMenu ordersMenu = new JMenu("Pedidos");
            
            menuBar.add(clientsMenu);
            menuBar.add(ordersMenu);
            window.setJMenuBar(menuBar);

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
                    requestsMenu();
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
                    ClientCrud.create();              
                    break;

                case 2:
                    ClientCrud.update();
                    break;

                case 3:
                    ClientCrud.delete();
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
            choice = scanner.nextInt();

            switch (choice) {
                case 1:    
                    RequestCrud.create();              
                    break;

                case 2:
                    RequestCrud.update();
                    break;

                case 3:
                    RequestCrud.delete();
                    break;
                
                case 4:
                    RequestCrud.getAll();
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
