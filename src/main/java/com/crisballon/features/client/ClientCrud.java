package com.crisballon.features.client;

import java.util.List;
import java.util.Scanner;

public class ClientCrud {
    private static final ClientRepository repository = new ClientRepository();
    // Scanner local para entrada de dados
    private static final Scanner scanner = new Scanner(System.in);

    public static void create() {
        System.out.print("\n==============================\n");
        System.out.print("--- Sistema da Cris Ballon --- \n\n");
        System.out.print("~ Cadastrar cliente ~\n\n");
        
        System.out.print("Nome: ");
        String name = scanner.nextLine();
        
        System.out.print("Telefone: ");
        String phone = scanner.nextLine();
        
        System.out.print("Endereço (opcional): ");
        String address = scanner.nextLine();
        
        System.out.print("Contato secundário (opcional): ");
        String secondaryContact = scanner.nextLine();
        
        Client newClient = new Client(name, phone, address, secondaryContact);
        repository.create(newClient);
        
        System.out.println("\nCliente cadastrado com sucesso!");
        System.out.print("\nPressione Enter para continuar...");
        scanner.nextLine();
    }

    public static void update() {
        System.out.print("\n==============================\n");
        System.out.print("--- Sistema da Cris Ballon --- \n\n");
        System.out.print("~ Editar cliente ~\n\n");
        
        // Mostrar lista de clientes primeiro
        listAll();
        
        // Verificar se existem clientes
        if (repository.getAll().isEmpty()) {
            System.out.print("\nNenhum cliente cadastrado!\n");
            System.out.print("Cadastre um cliente primeiro.\n\n");
            System.out.print("Pressione Enter para voltar . . .");
            scanner.nextLine();
            return;
        }
        
        System.out.print("\nID do cliente: ");
        Long id;
        try {
            id = scanner.nextLong();
            scanner.nextLine(); // Limpar buffer
        } catch (Exception e) {
            System.out.print("ID inválido! Digite apenas números.\n");
            scanner.nextLine(); // Limpar entrada inválida
            System.out.print("Pressione Enter para voltar . . .");
            scanner.nextLine();
            return;
        }
        
        Client client = repository.getById(id);
        if (client == null) {
            System.out.println("\nCliente não encontrado!");
            return;
        }
        
        System.out.println("\nDados atuais:");
        System.out.println("Nome: " + client.getName());
        System.out.println("Telefone: " + client.getPhone());
        System.out.println("Endereço: " + client.getAddress());
        System.out.println("Contato Secundário: " + client.getSecondaryContact());
        
        System.out.print("\nNovo nome: ");
        client.setName(scanner.nextLine());
        
        System.out.print("Novo telefone: ");
        client.setPhone(scanner.nextLine());
        
        System.out.print("Novo endereço: ");
        client.setAddress(scanner.nextLine());
        
        System.out.print("Novo contato secundário: ");
        client.setSecondaryContact(scanner.nextLine());
        
        repository.update(client);
        System.out.println("\nCliente atualizado com sucesso!");
        System.out.print("\nPressione Enter para continuar...");
        scanner.nextLine();
    }

    public static void delete() {
        System.out.print("\n==============================\n");
        System.out.print("--- Sistema da Cris Ballon --- \n\n");
        System.out.print("~ Remover cliente ~\n\n");
        
        // Mostrar lista de clientes primeiro
        listAll();
        
        // Verificar se existem clientes
        if (repository.getAll().isEmpty()) {
            System.out.print("\nNenhum cliente cadastrado!\n");
            System.out.print("Cadastre um cliente primeiro.\n\n");
            System.out.print("Pressione Enter para voltar . . .");
            scanner.nextLine();
            return;
        }
        
        System.out.print("\nID do cliente: ");
        Long id;
        try {
            id = scanner.nextLong();
            scanner.nextLine(); // Limpar buffer
        } catch (Exception e) {
            System.out.print("ID inválido! Digite apenas números.\n");
            scanner.nextLine(); // Limpar entrada inválida
            System.out.print("Pressione Enter para voltar . . .");
            scanner.nextLine();
            return;
        }
        
        Client client = repository.getById(id);
        if (client == null) {
            System.out.println("\nCliente não encontrado!");
            return;
        }
        
        repository.delete(client);
        System.out.println("\nCliente removido com sucesso!");
        System.out.print("\nPressione Enter para continuar...");
        scanner.nextLine();
    }

    public static void listAll() {
        List<Client> clients = repository.getAll();
        
        if (clients.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado!");
        } else {
            System.out.println("ID | Nome | Telefone");
            System.out.println("----------------------");
            for (Client client : clients) {
                System.out.printf("%d | %s | %s%n", 
                    client.getId(), client.getName(), client.getPhone());
            }
        }
    }

    public static void getAll() {
        System.out.print("\n==============================\n");
        System.out.print("--- Sistema da Cris Ballon --- \n\n");
        System.out.print("~ Lista de clientes ~\n\n");
        
        listAll();
        
        if (!repository.getAll().isEmpty()) {
            System.out.print("\nDeseja ver detalhes de algum cliente? (s/n): ");
            String response = scanner.nextLine();
            
            if (response.toLowerCase().equals("s")) {
                System.out.print("ID do cliente: ");
                try {
                    Long id = scanner.nextLong();
                    scanner.nextLine(); // Limpar buffer
                    showClientDetails(id);
                } catch (Exception e) {
                    System.out.print("ID inválido!\n");
                    scanner.nextLine(); // Limpar entrada inválida
                }
            }
        }
        
        System.out.print("\nPressione Enter para continuar...");
        scanner.nextLine();
    }
    
    private static void showClientDetails(Long id) {
        Client client = repository.getById(id);
        if (client == null) {
            System.out.print("\nCliente não encontrado!\n");
            return;
        }
        
        System.out.print("\n======== Detalhes do Cliente ========\n");
        System.out.printf("ID: %d\n", client.getId());
        System.out.printf("Nome: %s\n", client.getName());
        System.out.printf("Telefone: %s\n", client.getPhone());
        System.out.printf("Endereço: %s\n", 
            client.getAddress() != null && !client.getAddress().isEmpty() ? 
            client.getAddress() : "Não informado");
        System.out.printf("Contato Secundário: %s\n", 
            client.getSecondaryContact() != null && !client.getSecondaryContact().isEmpty() ? 
            client.getSecondaryContact() : "Não informado");
        System.out.print("====================================\n");
    }
}
