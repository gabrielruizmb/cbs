package com.example.features.client;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.example.features.userinterface.UserInterface;

public class ClientCrud {
    public static void create() {
        ClientRepository clientRepository = new ClientRepository(); 
        Client client = new Client(null, null, null, null, null);

        System.out.print("\n==============================\n");
        System.out.print("--- Sistema da Cris Ballon --- \n\n");
        System.out.print("~ Cadastrar cliente ~ \n\n");

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

        clientRepository.create(client);

        System.out.print("\nCliente criado com sucesso!\n\n");
        System.out.print("Pressione Enter para continuar . . .");
        UserInterface.scanner.nextLine();
    }

    public static void guiListAll(JFrame window) {
        String[] clientsTableColumns = {
            "ID", "Nome", "Telefone", "Endereço", "Contato secundário"
        };
        
        ClientRepository clientRepository = new ClientRepository(); 
        List<Client> clients = clientRepository.getAll();
        Object[][] clientsMatrix = {clients.toArray()};

        JPanel backgroundPanel = new JPanel();
        // backgroundPanel.setBounds(100, 100, 400, 400);
        // backgroundPanel.setBackground(Color.GREEN);
        // backgroundPanel.setSize(400, 400);
        backgroundPanel.setLayout(new GridLayout(1, 1));

        JTable clientsTable = new JTable(clientsMatrix, clientsTableColumns);

        JScrollPane scrollPane = new JScrollPane(clientsTable);
        // clientsTable.setFillsViewportHeight(true);

        backgroundPanel.add(scrollPane);

        window.getContentPane().add(backgroundPanel);

    }

    public static void listAll() {
        ClientRepository clientRepository = new ClientRepository(); 
        List<Client> clients = clientRepository.getAll();
    
        System.out.print("~ Lista de clientes ~ \n\n");
    
        for (Client client : clients) {
            System.out.printf("ID: %d \n", client.getId());
            System.out.printf("Nome: %s \n", client.getName());
            System.out.printf("Telefone: %s \n", client.getPhone());
    
            if (client.getAdress() != null && !client.getAdress().isBlank())
                System.out.printf("Endereço: %s \n", client.getAdress());
    
            if (client.getSecondaryContact() != null && !client.getSecondaryContact().isBlank())
                System.out.printf("Contato secundário: %s \n", client.getSecondaryContact());
    
            System.out.print("\n---------------------------------------\n\n");
        }
    }

    public static void getAll() {
        System.out.print("\n==============================\n");
        System.out.print("--- Sistema da Cris Ballon --- \n\n");
        listAll();

        UserInterface.scanner.nextLine();
        System.out.print("Pressione Enter para voltar . . .");
        UserInterface.scanner.nextLine();
    }

    public static void update() {
        listAll();
    
        System.out.print("\n~ Editar cliente ~\n");
        System.out.print("Escolha um cliente pelo ID para editar: ");
        
        Long id = UserInterface.scanner.nextLong();
        UserInterface.scanner.nextLine(); // Limpa o buffer
        
        ClientRepository clientRepository = new ClientRepository(); 
        Client client = clientRepository.getById(id);
        
        if (client == null) {
            System.out.print("\nID inválido! Tente novamente.\n\n");
            waitForEnter();
            return;
        }
        
        System.out.print("Novo nome (" + client.getName() + "): ");
        String newName = UserInterface.scanner.nextLine();
        if (!newName.isBlank()) {
            while (!client.setName(newName)) {
                System.out.print("O nome não pode ficar em branco!\nNovo nome: ");
                newName = UserInterface.scanner.nextLine();
            }
        }
        
        System.out.print("Novo telefone (" + client.getPhone() + "): ");
        String newPhone = UserInterface.scanner.nextLine();
        if (!newPhone.isBlank()) {
            while (!client.setPhone(newPhone)) {
                System.out.print("O telefone não pode ficar em branco!\nNovo telefone: ");
                newPhone = UserInterface.scanner.nextLine();
            }
        }
        
        System.out.print("Novo endereço (" + (client.getAdress() != null ? client.getAdress() : "vazio") + "): ");
        String newAddress = UserInterface.scanner.nextLine();
        if (!newAddress.isBlank()) {
            client.setAdress(newAddress);
        }
        
        System.out.print("Novo contato secundário (" + 
            (client.getSecondaryContact() != null ? client.getSecondaryContact() : "vazio") + "): ");
        String newContact = UserInterface.scanner.nextLine();
        if (!newContact.isBlank()) {
            client.setSecondaryContact(newContact);
        }
        
        clientRepository.update(client);
        System.out.print("\nCliente editado com sucesso!\n\n");
        waitForEnter();
    }

    public static void delete() {
        listAll();
    
        System.out.print("\n~ Excluir cliente ~\n");
        System.out.print("Escolha um cliente pelo ID para excluir: ");
        
        Long id = UserInterface.scanner.nextLong();
        UserInterface.scanner.nextLine(); // Limpa o buffer
        
        ClientRepository clientRepository = new ClientRepository(); 
        Client client = clientRepository.getById(id);
        
        if (client == null) {
            System.out.print("\nID inválido! Tente novamente.\n\n");
            waitForEnter();
            return;
        }
        
        System.out.print("Tem certeza que deseja excluir " + client.getName() + "? (s/n): ");
        String confirm = UserInterface.scanner.nextLine();
        
        if (confirm.equalsIgnoreCase("s")) {
            clientRepository.delete(client);
            System.out.print("\nCliente excluído com sucesso!\n\n");
        } else {
            System.out.print("\nOperação cancelada!\n\n");
        }
        
        waitForEnter();
    }

    private static void waitForEnter() {
        System.out.print("\nPressione Enter para voltar . . .");
        UserInterface.scanner.nextLine();
    }
}
