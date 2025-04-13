package com.example.features.client;

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

        System.out.print("\nEndereço(opcional): ");
        client.setAdress(UserInterface.scanner.nextLine());

        System.out.print("\nContato secundário, ex: email, tel.(Opcional): ");
        client.setSecondaryContact(UserInterface.scanner.nextLine());

        Main.clientRepository.create(client);
    }
}
