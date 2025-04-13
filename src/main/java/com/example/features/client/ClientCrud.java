package com.example.features.client;

import java.util.List;

import com.example.Main;
import com.example.features.userinterface.UserInterface;

public class ClientCrud {
    public static void createClient() {
        Client client = new Client(null, null, null, null, null);

        System.out.print("\n==============================\n");
        System.out.print("--- Sistema da Cris Ballon --- \n\n");
        System.out.print("~~~ Cadastrar cliente ~~~ \n\n");

        System.out.print("Nome: ");
        UserInterface.scanner.nextLine();   // Limpa o buffer do teclado.

        // Enquanto o nome estiver em branco, avisa ao 
        // usuário e pede para ele informar novamente.
        while (!client.setName(UserInterface.scanner.nextLine())) {
            System.out.print("O nome não pode ficar em branco!\nNome: ");
        }
        
        System.out.print("Telefone: ");
        // Enquanto o telefone estiver em branco, avisa ao 
        // usuário e pede para ele informar novamente.
        while (!client.setPhone(UserInterface.scanner.nextLine())) {
            System.out.print("O telefone não pode ficar em branco!\n");
            System.out.print("Telefone: ");
        }

        System.out.print("Endereço(opcional): ");
        client.setAdress(UserInterface.scanner.nextLine());

        System.out.print("Contato secundário, ex: email, tel.(Opcional): ");
        client.setSecondaryContact(UserInterface.scanner.nextLine());

        Main.clientRepository.create(client);

        System.out.print("\nCliente criado com sucesso!\n\n");
        System.out.print("Pressione Enter para continuar . . .");
        UserInterface.scanner.nextLine();
    }

    public static void getAll() {
        List<Client> clients = Main.clientRepository.getAll();

        System.out.print("\n==============================\n");
        System.out.print("--- Sistema da Cris Ballon --- \n\n");
        System.out.print("~~~ Lista de clientes ~~~ \n\n");

        for (Client client : clients) {
            System.out.printf("Nome: %s \n", client.getName());
            System.out.printf("Telefone: %s \n", client.getPhone());

            if (!client.getAdress().isBlank())
                System.out.printf("Endereço: %s \n", client.getAdress());

            if (!client.getSecondaryContact().isBlank())
                System.out.printf("Contato secundário: %s \n", 
                                  client.getSecondaryContact());

            System.out.print("\n---------------------------------------\n\n");
        }

        UserInterface.scanner.nextLine();
        System.out.print("Pressione Enter para voltar . . .");
        UserInterface.scanner.nextLine();
    }
}
