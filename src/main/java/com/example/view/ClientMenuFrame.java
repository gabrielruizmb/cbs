package com.example.view;

import javax.swing.*;
import java.awt.*;
import com.example.features.client.ClientCrud;

public class ClientMenuFrame extends JFrame{
    
    public ClientMenuFrame() {
        setTitle("Clientes - Cris Ballon Ateliê");
        setSize(380, 260);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setLayout(new GridLayout(6,1,10,10));
        add(new JLabel("Menu de Clientes", SwingConstants.CENTER));

        JButton btnAdd = new JButton("Adicionar cliente");
        JButton btnEdit = new JButton("Editar Cliente");
        JButton btnDelete = new JButton("Deletar Cliente");
        JButton btnList = new JButton("Listar Clientes");
        JButton btnBack = new JButton("Voltar");

        add(btnAdd);
        add(btnEdit);
        add(btnDelete);
        add(btnList);
        add(btnBack);

        btnAdd.addActionListener(e -> ClientCrud.create());  //vão abrir o terminal pois suas telas ainda não foram criadas
        btnEdit.addActionListener(e -> ClientCrud.update());
        btnDelete.addActionListener(e -> ClientCrud.delete());
        btnList.addActionListener(e -> ClientCrud.getAll());
        btnBack.addActionListener(e -> dispose());
    }

}
