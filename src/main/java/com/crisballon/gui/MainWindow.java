package com.crisballon.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.crisballon.features.user.UserManagement;
import com.crisballon.features.user.User;

/**
 * Janela principal do sistema Cris Ballon Ateliê
 * Controla o menu principal e login de usuários
 */
public class MainWindow extends JFrame {
    private User currentUser;
    private JLabel userLabel;
    
    // Construtor - inicializa a janela e mostra login
    public MainWindow() {
        initializeComponents();
        showLoginDialog();
    }
    
    // Configura a janela principal
    private void initializeComponents() {
        setTitle("Cris Ballon Ateliê");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
        
        // Cabeçalho com logo e título
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.BLACK);
        headerPanel.setPreferredSize(new Dimension(800, 80));
        
        // Painel do logo
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setBackground(Color.BLACK);
        
        // Carrega o logo ou mostra "CB" se não encontrar
        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("/logo.jpeg"));
            if (logoIcon.getIconWidth() > 0) {
                Image img = logoIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                logoPanel.add(new JLabel(new ImageIcon(img)));
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            JLabel logoLabel = new JLabel("CB");
            logoLabel.setFont(new Font("Arial", Font.BOLD, 20));
            logoLabel.setForeground(Color.WHITE);
            logoPanel.add(logoLabel);
        }
        
        headerPanel.add(logoPanel, BorderLayout.WEST);
        
        JLabel titleLabel = new JLabel("Cris Ballon Ateliê", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        userLabel = new JLabel("", JLabel.RIGHT);
        userLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        userLabel.setForeground(Color.WHITE);
        userLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        headerPanel.add(userLabel, BorderLayout.EAST);
        
        add(headerPanel, BorderLayout.NORTH);
        
        // Cria o menu principal
        createMainMenu();
    }
    
    // Cria os botões do menu principal
    private void createMainMenu() {
        JPanel mainMenuPanel = new JPanel(new BorderLayout());
        mainMenuPanel.setBackground(Color.WHITE);
        
        // Botões principais em grade 3x2
        JPanel menuPanel = new JPanel(new GridLayout(3, 2, 20, 20));
        menuPanel.setBackground(Color.WHITE);
        menuPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 20, 100));
        
        String[] buttonTexts = {"Clientes", "Produtos", "Pedidos", "Vendas", "Usuários", "Trocar Usuário"};
        
        for (int i = 0; i < buttonTexts.length; i++) {
            JButton button = createMenuButton(buttonTexts[i]);
            button.addActionListener(new MenuClique(i));
            
            // Esconde botão Usuários se não for admin
            if (i == 4 && (currentUser == null || !UserManagement.isAdmin())) {
                button.setVisible(false);
            }
            
            menuPanel.add(button);
        }
        
        // Botão Sair 
        JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        exitPanel.setBackground(Color.WHITE);
        exitPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 50, 0));
        
        JButton exitButton = createMenuButton("Sair");
        exitButton.addActionListener(new MenuClique(6));
        exitPanel.add(exitButton);
        
        mainMenuPanel.add(menuPanel, BorderLayout.CENTER);
        mainMenuPanel.add(exitPanel, BorderLayout.SOUTH);
        add(mainMenuPanel, BorderLayout.CENTER);
    }
    
    // Cria um botão do menu com estilo padrão
    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(200, 80));
        button.setFocusPainted(false);
        return button;
    }
    
    // Trata os cliques nos botões do menu
    private void handleMenuClick(int option) {
        switch (option) {
            case 0: new ClientWindow(this).setVisible(true); break;
            case 1: new ProductWindow(this).setVisible(true); break;
            case 2: new RequestWindow(this).setVisible(true); break;
            case 3: new SalesWindow(this).setVisible(true); break;
            case 4: if (UserManagement.isAdmin()) new UserWindow(this).setVisible(true); break;
            case 5: showLoginDialog(); break;
            case 6: UserManagement.logout(); System.exit(0); break;
        }
    }
    
    // Mostra a janela de login
    private void showLoginDialog() {
        UserManagement.initializeDefaultAdmin();
        
        JDialog loginDialog = new JDialog(this, "Login", true);
        loginDialog.setSize(400, 300);
        loginDialog.setLocationRelativeTo(this);
        loginDialog.setLayout(new GridBagLayout());
        loginDialog.getContentPane().setBackground(Color.WHITE);
        
        // Impede acesso ao sistema se o login for fechado sem autenticação
        loginDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        loginDialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                if (currentUser == null) {
                    System.exit(0);
                }
            }
        });
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Título
        JLabel titleLabel = new JLabel("Cris Ballon Ateliê");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        loginDialog.add(titleLabel, gbc);
        
        // Campos de usuário e senha
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        loginDialog.add(new JLabel("Usuário:"), gbc);
        JTextField userField = new JTextField(15);
        gbc.gridx = 1;
        loginDialog.add(userField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        loginDialog.add(new JLabel("Senha:"), gbc);
        JPasswordField passField = new JPasswordField(15);
        gbc.gridx = 1;
        loginDialog.add(passField, gbc);
        
        // Botão de login
        JButton loginButton = new JButton("Entrar");
        loginButton.setBackground(Color.BLACK);
        loginButton.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        loginDialog.add(loginButton, gbc);
        
        loginButton.addActionListener(new LoginClique(loginDialog, userField, passField));
        loginDialog.setVisible(true);
    }
    
    // Atualiza o menu após login
    private void refreshMenu() {
        getContentPane().removeAll();
        initializeComponents();
        revalidate();
        repaint();
    }
    
    // Main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainWindow().setVisible(true));
    }
    
    // Classe para tratar cliques nos botões do menu
    private class MenuClique implements ActionListener {
        private int opcao;
        
        public MenuClique(int opcao) {
            this.opcao = opcao;
        }
        
        public void actionPerformed(ActionEvent e) {
            handleMenuClick(opcao);
        }
    }
    
    // Classe para tratar o login do usuário
    private class LoginClique implements ActionListener {
        private JDialog dialog;
        private JTextField userField;
        private JPasswordField passField;
        
        public LoginClique(JDialog dialog, JTextField userField, JPasswordField passField) {
            this.dialog = dialog;
            this.userField = userField;
            this.passField = passField;
        }
        
        public void actionPerformed(ActionEvent e) {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            
            User user = UserManagement.login(username, password);
            if (user != null) {
                currentUser = user;
                userLabel.setText("Usuário: " + user.getUsername() + " (" + user.getRole().getDisplayName() + ")");
                dialog.dispose();
                refreshMenu();
            } else {
                JOptionPane.showMessageDialog(dialog, "Credenciais inválidas!");
            }
        }
    }
}
