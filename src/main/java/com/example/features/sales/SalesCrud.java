package com.example.features.sales;

import java.util.List;
import com.example.features.request.Request;
import com.example.features.request.RequestRepository;
import com.example.features.userinterface.UserInterface;

public class SalesCrud {
    
    public static void salesMenu() {
        int choice = 0;
        
        while (choice != 3) {
            System.out.print("\n==============================\n");
            System.out.print("--- Sistema da Cris Ballon --- \n\n");
            System.out.print("~~~ Menu de vendas ~~~ \n\n");
    
            System.out.print(" 1.Confirmar vendas \n 2.Ver vendas \n");
            System.out.print(" 3.Voltar \n\n");

            System.out.print("Escolha uma opção: ");
            choice = UserInterface.getValidIntInput();

            switch (choice) {
                case 1: confirmSales(); break;
                case 2: viewSales(); break;
                case 3: break;
                default:
                    System.out.print("\nOpção inválida!\n");
                    System.out.print("\nPressione Enter para continuar . . .");
                    UserInterface.scanner.nextLine();
            }
        }
    }

    public static void confirmSales() {
        RequestRepository requestRepository = new RequestRepository();
        List<Request> pendingRequests = requestRepository.getPendingRequests();

        System.out.print("\n==============================\n");
        System.out.print("--- Sistema da Cris Ballon --- \n\n");
        System.out.print("~ Confirmar vendas ~ \n\n");

        if (pendingRequests.isEmpty()) {
            System.out.print("Nenhum pedido pendente encontrado!\n\n");
            System.out.print("Pressione Enter para voltar . . .");
            UserInterface.scanner.nextLine();
            return;
        }

        for (Request request : pendingRequests) {
            System.out.printf("ID: %d | Cliente: %s | Descrição: %s | Preço: R$ %.2f\n", 
                request.getId(), request.getClient().getName(), 
                request.getDescription(), request.getPrice());
            System.out.print("Confirmar venda? (1-Sim / 2-Não): ");
            
            int confirm = UserInterface.getValidIntInput();
            if (confirm == 1) {
                request.setConfirmed(true);
                requestRepository.update(request);
                System.out.print("Venda confirmada!\n\n");
            } else {
                System.out.print("Venda não confirmada.\n\n");
            }
        }

        System.out.print("Pressione Enter para voltar . . .");
        UserInterface.scanner.nextLine();
    }

    public static void viewSales() {
        RequestRepository requestRepository = new RequestRepository();
        List<Request> confirmedSales = requestRepository.getConfirmedSales();

        System.out.print("\n==============================\n");
        System.out.print("--- Sistema da Cris Ballon --- \n\n");
        System.out.print("~ Vendas confirmadas ~ \n\n");

        if (confirmedSales.isEmpty()) {
            System.out.print("Nenhuma venda confirmada encontrada!\n\n");
            System.out.print("Pressione Enter para voltar . . .");
            UserInterface.scanner.nextLine();
            return;
        }

        double total = 0.0;
        for (Request sale : confirmedSales) {
            System.out.printf("ID: %d | Cliente: %s | Descrição: %s | Valor: R$ %.2f\n", 
                sale.getId(), sale.getClient().getName(), 
                sale.getDescription(), sale.getPrice());
            total += sale.getPrice();
            System.out.print("========================================\n");
        }

        System.out.printf("\nTOTAL DE VENDAS: R$ %.2f\n\n", total);
        System.out.print("Pressione Enter para voltar . . .");
        UserInterface.scanner.nextLine();
    }
}