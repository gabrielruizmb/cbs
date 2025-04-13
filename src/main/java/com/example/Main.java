package com.example;

import javax.persistence.EntityManager;

import com.example.configs.CustomizerFactory;
import com.example.features.client.ClientRepository;

import com.example.features.userinterface.UserInterface;

public class Main {
    // Objeto que se conecta com nosso banco de dados.
    private static EntityManager em = CustomizerFactory.getEntityManager();

    // Repositório que pode ser acessado em toda a aplicação para fazer CRUD
    // de cliente no nosso banco de dados que foi conectado pelo EntityManager.
    public static ClientRepository clientRepository = new ClientRepository(em);   

    public static void main(String[] args) {   
        // Exibe o menu principal.
        UserInterface.mainMenu();   
    }
}