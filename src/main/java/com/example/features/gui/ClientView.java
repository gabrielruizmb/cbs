package com.example.features.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.example.features.client.Client;
import com.example.features.client.ClientRepository;

public class ClientView {

    public static JScrollPane listAll() {
        
        ClientRepository clientRepository = new ClientRepository();

        List<Client> clients = clientRepository.getAll();
        List<String> clientsNames = new ArrayList<>();

        for (Client client : clients) {
            clientsNames.add(client.getName() + '/' + client.getPhone());
        }

        JList clientsList = new JList<>(clientsNames.toArray());

        clientsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        clientsList.setLayoutOrientation(JList.VERTICAL);

        JScrollPane clientsListPane = new JScrollPane(clientsList);
        clientsListPane.setBounds(50, 50, 250, 80);
        // clientsListPane.setPreferredSize(new DimensionUIResource(250, 80));

        return clientsListPane;
    }
}
