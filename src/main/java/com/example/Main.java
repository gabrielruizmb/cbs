package com.example;

// import javax.persistence.EntityManager;

// import com.example.configs.CustomizerFactory;
// import com.example.features.client.Client;
// import com.example.features.client.ClientRepository;
import com.example.features.userinterface.UserInterface;

public class Main {
    public static void main(String[] args) {      
        // Exibe o menu principal.
        UserInterface.mainMenu();   
    }
}

    // Exemplo de como salvar algo no banco de dados.

    // System.out.println("Hello world!");

    // EntityManager em = CustomizerFactory.getEntityManager();
    // ClientRepository clientRepository = new ClientRepository(em);

    // Client client = new Client(
    //     null,
    //     "Amanda Prado", 
    //     "+5545988772299", 
    //     null, 
    //     null
    // );

    // clientRepository.create(client);