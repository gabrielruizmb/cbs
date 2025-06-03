package com.example;

import javax.swing.JFrame;

import com.example.features.userinterface.UserInterface;

public class Main {
    
    public static void main(String[] args) { 
        
        JFrame window = new JFrame();

        window.setLayout(null);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
        window.setBounds(250, 50, 800, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Sistema da Cris ballon");

        // Exibe o menu principal.
        UserInterface.mainMenu(window);   
    }
}