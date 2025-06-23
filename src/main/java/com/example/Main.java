package com.example;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import com.example.features.gui.ClientView;
import com.example.features.userinterface.UserInterface;

public class Main {
    
    public static void main(String[] args) { 
        
        JFrame window = new JFrame();
        JPanel genericPanel = new JPanel();
        
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(1080, 720);
        window.setVisible(true);
        window.setLayout(null);

        genericPanel.setSize(1080, 720);
        genericPanel.setBackground(Color.PINK);

        window.getContentPane().add(genericPanel);

        JMenuBar mainMenuBar = new JMenuBar();

        JButton homeButton = new JButton("Sistema da Cris Ballon");
        JButton clientsButton = new JButton("Clientes");
        JButton ordersButton = new JButton("Pedidos");

        mainMenuBar.add(homeButton);       
        mainMenuBar.add(clientsButton);
        mainMenuBar.add(ordersButton);

        homeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                genericPanel.removeAll();
                genericPanel.repaint();
            }
            
        });

        clientsButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                genericPanel.add(ClientView.listAll());
                // genericPanel.setSize(500, 500);
                genericPanel.revalidate();
                genericPanel.repaint();
            }

        });

        ordersButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                genericPanel.removeAll();
                genericPanel.repaint();
            }
            
        });

        window.setJMenuBar(mainMenuBar);
        window.revalidate();
        window.repaint();

        // Exibe o menu principal de texto.
        // UserInterface.mainMenu();   

        // ClientView.listAll(window);
    }
}