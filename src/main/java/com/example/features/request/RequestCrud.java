package com.example.features.request;

import java.util.List;

import com.example.features.client.Client;
import com.example.features.client.ClientCrud;
import com.example.features.client.ClientRepository;
import com.example.features.userinterface.UserInterface;

public class RequestCrud {
    public static void create() {
        RequestRepository requestRepository = new RequestRepository();
        ClientRepository clientRepository = new ClientRepository();
        Request request = new Request();
        Client client = new Client();

        System.out.print("\n==============================\n");
        System.out.print("--- Sistema da Cris Ballon --- \n\n");
        System.out.print("~ Novo pedido ~ \n\n");

        ClientCrud.listAll();
        System.out.print("Escolha um cliente pelo ID: ");
        client = clientRepository.getById(UserInterface.scanner.nextLong());

        while (client == null) {
            System.out.print("ID inválido!\n");
            System.out.print("Escolha um cliente pelo ID ");
            System.out.print("dentre os listados acima: ");
            client = clientRepository.getById(UserInterface.scanner.nextLong());
        }

        request.setClient(client);
        request.setStatus(RequestStatus.STATUS1.getName());

        System.out.print("Descrição do pedido: ");
        UserInterface.scanner.nextLine();
        while (!request.setDescription(UserInterface.scanner.nextLine())) {
            System.out.print("A descrição não pode ficar em branco!\n");
            System.out.print("Descrição do pedido: ");
        }

        requestRepository.create(request);
        
        System.out.print("Pedido criado com sucesso!\n\n");
        System.out.print("Pressione 'Enter' para voltar . . .");
        UserInterface.scanner.nextLine();
    }

    public static void listAll() {
        RequestRepository requestRepository = new RequestRepository();
        List<Request> requests = requestRepository.getAll();

        for (Request request : requests) {
            System.out.printf("ID do pedido: %d \n\n", request.getId());
            System.out.printf("Nome do cliente: %s \n\n", request.getClient().getName());
            System.out.printf("Status do pedido: %s \n\n", request.getStatus());
            System.out.printf("Descrição do pedido: %s \n\n", request.getDescription());
            System.out.print("========================================\n\n");
        }
    }

    public static void getAll() {
        System.out.print("\n==============================\n");
        System.out.print("--- Sistema da Cris Ballon --- \n\n");
        System.out.print("~ Lista de pedidos~ \n\n");

        listAll();

        UserInterface.scanner.nextLine();
        System.out.print("Pressione 'Enter' para voltar . . .");
        UserInterface.scanner.nextLine();
    }

    public static void update() {
        RequestRepository requestRepository = new RequestRepository();
        Request request = new Request();
        int status = 0;

        System.out.print("\n==============================\n");
        System.out.print("--- Sistema da Cris Ballon --- \n\n");
        System.out.print("~ Editar pedido ~ \n\n");

        listAll();

        System.out.print("Escolha um pedido pelo ID para editar: ");
        request = requestRepository.getById(UserInterface.scanner.nextLong());

        while (request == null) {
            System.out.print("\nID de pedido inválido!\n");
            System.out.print("Escolha um pedido pelo ID ");
            System.out.print("dentre os listados acima: ");
            request = requestRepository
                      .getById(UserInterface.scanner.nextLong());        
        }

        System.out.printf("Deseja alterar o status do pedido?(%s) (s/n)", request.getStatus());
        if (UserInterface.scanner.next().equals("s")) {
            System.out.print("Escolha um status dentre os seguintes: \n\n");
            System.out.printf("1)%s \n", RequestStatus.STATUS1.getName());
            System.out.printf("2)%s \n", RequestStatus.STATUS2.getName());
            System.out.printf("3)%s \n", RequestStatus.STATUS3.getName());
            System.out.printf("4)%s \n", RequestStatus.STATUS4.getName());
            System.out.printf("5)%s \n", RequestStatus.STATUS5.getName());

            while (status == 0) {
                System.out.printf("\nNovo status(%s): ", request.getStatus());
                status = UserInterface.scanner.nextInt();

                switch (status) {
                    case 1:
                        request.setStatus(RequestStatus.STATUS1.getName());
                        break;
    
                    case 2:
                        request.setStatus(RequestStatus.STATUS2.getName());
                        break;
    
                    case 3:
                        request.setStatus(RequestStatus.STATUS3.getName());
                        break;
    
                    case 4:
                        request.setStatus(RequestStatus.STATUS4.getName());
                        break;
    
                    case 5:
                        request.setStatus(RequestStatus.STATUS5.getName());
                        break;
    
                    default:
                        status = 0;
                        System.out.print("Status inválido! escolha novamente . . .");
                        break;
                }
            }
        }

        System.out.printf("Deseja alterar a descrição do pedido(%s)? (s/n)", request.getDescription());
        if (UserInterface.scanner.next().equals("s")) {
            System.out.printf("Nova descrição(%s): ", request.getDescription());
            UserInterface.scanner.nextLine();
            request.setDescription(UserInterface.scanner.nextLine());
        }

        requestRepository.update(request);

        System.out.print("Pedido atualizado!\n\n");
        System.out.print("Pressione 'Enter' para voltar . . .");
    }

    public static void delete() {
        RequestRepository requestRepository = new RequestRepository();
        Request request = new Request();

        System.out.print("\n==============================\n");
        System.out.print("--- Sistema da Cris Ballon --- \n\n");
        System.out.print("~ Excluir pedido ~ \n\n");

        listAll();

        System.out.print("Escolha um pedido pelo ID para excluir: ");
        request = requestRepository.getById(UserInterface.scanner.nextLong());

        while (request == null) {
            System.out.print("\nID de pedido inválido!\n");
            System.out.print("Escolha um pedido pelo ID para excluir: ");
            request = requestRepository.getById(UserInterface.scanner.nextLong());
        }

        System.out.print("Deseja realmente excluir este pedido(s/n)? ");
        if (UserInterface.scanner.next().equals("s")) {
            requestRepository.delete(request);
            System.out.print("\nPedido excluido!\n\n");
        } else
            System.out.print("\nOperação cancelada.\n\n");
        
        UserInterface.scanner.nextLine();
        System.out.print("Pressione 'Enter' para voltar ao menu . . .");    
        UserInterface.scanner.nextLine();
    }
}
