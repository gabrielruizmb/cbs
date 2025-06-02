package com.example;

import java.awt.Color;

import javax.swing.JFrame;

import com.example.features.userinterface.UserInterface;

public class Main {
    
    public static void main(String[] args) { 
        
        JFrame window = new JFrame();

        window.setLayout(null);
        window.setVisible(true);
        window.setBounds(250, 50, 800, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Exibe o menu principal.
        UserInterface.mainMenu(window);   
    }
}