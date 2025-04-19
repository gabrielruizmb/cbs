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

        System.out.print("\n==============================\n");
        System.out.print("--- Sistema da Cris Ballon --- \n\n");
        System.out.print("~ Editar pedido ~ \n\n");

        listAll();

        System.out.print("Escolha um pedido pelo ID para editar: ");
        request = requestRepository.getById(UserInterface.scanner.nextLong());

        while (request == null) {
            System.out.print("ID de pedido inválido!\n");
            System.out.print("Escolha um pedido pelo ID ");
            System.out.print("dentre os listados acima: ");
            request = requestRepository
                      .getById(UserInterface.scanner.nextLong());        
        }
    }
}
