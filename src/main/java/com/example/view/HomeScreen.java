package com.example.view;

import javax.swing.*;
import java.awt.*;
import com.example.features.userinterface.UserInterface;

public class HomeScreen extends JFrame {

    public HomeScreen() {
        setTitle("Cris Ballon Ateliê");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridLayout(5,1,10,10));
        JButton btnClients = new JButton("Clientes");
        JButton btnOrders = new JButton("Pedidos");
        JButton bntManual = new JButton("Manual do Usuário");
        JButton btnExit = new JButton("Sair");

        //adicionando os botões
        add(new JLabel("Bem-vindo(a) ao sistema Cris Ballon Ateliê!", SwingConstants.CENTER));
        add(btnClients);
        add(btnOrders);
        add(bntManual);
        add(btnExit);

        //Aqui é onde os botões chamam as funçoes do código
        btnClients.addActionListener(e -> UserInterface.clientMenu()); //vão abrir o terminal pois a tela em swing ainda não foi criada
        btnOrders.addActionListener(e -> UserInterface.requestsMenu());
        bntManual.addActionListener(e -> UserInterface.userManual());
        btnExit.addActionListener(e -> System.exit(0));

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new HomeScreen().setVisible(true);
        });
        
    }
}
