package com.example.features.request;

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
}
