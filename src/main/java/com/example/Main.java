package com.example;

import javax.persistence.EntityManager;

import com.example.features.Client.Client;
import com.example.features.Client.ClientRepository;
import com.example.resitory.CustomizerFactory;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        EntityManager em = CustomizerFactory.getEntityManager();
        ClientRepository clientRepository = new ClientRepository(em);

        Client client = new Client(
            null,
            "Jonas Melo", 
            "+5545988772299", 
            null, 
            null
        );

        clientRepository.create(client);
    }
}