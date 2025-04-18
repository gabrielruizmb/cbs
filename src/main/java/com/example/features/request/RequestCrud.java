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

        // ClientCrud.getAll();
        // System.out.print("Escolha um cliente pelo ID: ");
        // while (client == null) {
        //     client = clientRepository.getById(UserInterface.scanner.nextLong());
        //     System.out.print("ID inválido!\n");
        //     System.out.print("Escolha um cliente pelo ID: ");
        // }
    }
}
