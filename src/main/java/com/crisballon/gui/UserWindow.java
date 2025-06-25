package com.crisballon.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import com.crisballon.features.user.User;
import com.crisballon.features.user.UserRepository;
import com.crisballon.features.user.UserRole;
import com.crisballon.features.user.AuditLog;
import com.crisballon.features.user.AuditLogRepository;

public class UserWindow extends JFrame {
    private UserRepository userRepository;
    private AuditLogRepository auditRepository;
    private JTable userTable, auditTable;
    private DefaultTableModel userModel, auditModel;
    private JTextField usernameField, passwordField;
    private JComboBox<UserRole> roleCombo;
    private Long editingUserId = null;
    
    public UserWindow(JFrame parent) {
        this.userRepository = new UserRepository();
        this.auditRepository = new AuditLogRepository();
        initializeComponents();
        loadUsers();
        loadAuditLogs();
        setLocationRelativeTo(parent);
    }
    
    private void initializeComponents() {
        setTitle("Usuários");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
        
        // Cabeçalho
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.BLACK);
        JLabel titleLabel = new JLabel("Gerenciar Usuários");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Painel principal com abas
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(Color.WHITE);
        
        // Aba de usuários
        JPanel usersPanel = createUsersPanel();
        tabbedPane.addTab("Usuários", usersPanel);
        
        // Aba de auditoria
        JPanel auditPanel = createAuditPanel();
        tabbedPane.addTab("Logs de Auditoria", auditPanel);
        
        add(tabbedPane, BorderLayout.CENTER);
        
        // Botão fechar
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBackground(Color.WHITE);
        
        JButton closeButton = new JButton("Fechar");
        closeButton.setBackground(Color.LIGHT_GRAY);
        closeButton.setForeground(Color.BLACK);
        closeButton.addActionListener(new FecharUsuarioClique());
        
        bottomPanel.add(closeButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createUsersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Painel do formulário
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder("Dados do Usuário"));
        
        formPanel.add(new JLabel("Nome de Usuário:"));
        usernameField = new JTextField();
        formPanel.add(usernameField);
        
        formPanel.add(new JLabel("Senha:"));
        passwordField = new JTextField();
        formPanel.add(passwordField);
        
        formPanel.add(new JLabel("Tipo:"));
        roleCombo = new JComboBox<>(UserRole.values());
        formPanel.add(roleCombo);
        
        // Botões
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);
        
        JButton saveButton = new JButton("Salvar");
        saveButton.setBackground(Color.BLACK);
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(new SalvarUsuarioClique());
        
        JButton deleteButton = new JButton("Excluir");
        deleteButton.setBackground(Color.BLACK);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener(new ExcluirUsuarioClique());
        
        JButton clearButton = new JButton("Limpar");
        clearButton.setBackground(Color.LIGHT_GRAY);
        clearButton.setForeground(Color.BLACK);
        clearButton.addActionListener(new LimparUsuarioClique());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        
        formPanel.add(buttonPanel);
        
        panel.add(formPanel, BorderLayout.NORTH);
        
        // Tabela
        String[] columns = {"ID", "Usuário", "Tipo", "Criado"};
        userModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        userTable = new JTable(userModel);
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userTable.getTableHeader().setBackground(Color.LIGHT_GRAY);
        userTable.addMouseListener(new TabelaUsuarioClique());
        
        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Lista de Usuários"));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createAuditPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Tabela
        String[] columns = {"Data/Hora", "Usuário", "Ação", "Detalhes"};
        auditModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        auditTable = new JTable(auditModel);
        auditTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        auditTable.getTableHeader().setBackground(Color.LIGHT_GRAY);
        
        JScrollPane scrollPane = new JScrollPane(auditTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Logs de Auditoria"));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Botão atualizar
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);
        
        JButton refreshButton = new JButton("Atualizar");
        refreshButton.setBackground(Color.BLACK);
        refreshButton.setForeground(Color.WHITE);
        refreshButton.addActionListener(new AtualizarLogsClique());
        
        buttonPanel.add(refreshButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void loadUsers() {
        userModel.setRowCount(0);
        List<User> users = userRepository.getAll();
        
        for (User user : users) {
            Object[] row = {
                user.getId(),
                user.getUsername(),
                user.getRole().getDisplayName(),
                user.getCreatedAt().toString().substring(0, 19)
            };
            userModel.addRow(row);
        }
    }
    
    private void loadAuditLogs() {
        auditModel.setRowCount(0);
        List<AuditLog> logs = auditRepository.getAll();
        
        for (AuditLog log : logs) {
            Object[] row = {
                log.getTimestamp().toString().substring(0, 19),
                log.getUsername(),
                log.getAction(),
                log.getDetails()
            };
            auditModel.addRow(row);
        }
    }
    
    private void loadUserToForm(int row) {
        Long userId = (Long) userModel.getValueAt(row, 0);
        User user = userRepository.getById(userId);
        
        if (user != null) {
            editingUserId = userId;
            usernameField.setText(user.getUsername());
            passwordField.setText(user.getPassword());
            roleCombo.setSelectedItem(user.getRole());
        }
    }
    
    private void saveUser() {
        if (usernameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome de usuário é obrigatório!");
            return;
        }
        
        if (passwordField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Senha é obrigatória!");
            return;
        }
        
        User user;
        if (editingUserId != null) {
            user = userRepository.getById(editingUserId);
        } else {
            if (userRepository.findByUsername(usernameField.getText().trim()) != null) {
                JOptionPane.showMessageDialog(this, "Nome de usuário já existe!");
                return;
            }
            user = new User();
        }
        
        user.setUsername(usernameField.getText().trim());
        user.setPassword(passwordField.getText().trim());
        user.setRole((UserRole) roleCombo.getSelectedItem());
        
        if (editingUserId != null) {
            userRepository.update(user);
            JOptionPane.showMessageDialog(this, "Usuário atualizado!");
        } else {
            userRepository.create(user);
            JOptionPane.showMessageDialog(this, "Usuário criado!");
        }
        
        clearFields();
        loadUsers();
        loadAuditLogs();
    }
    
    private void deleteUser() {
        if (editingUserId == null) {
            JOptionPane.showMessageDialog(this, "Selecione um usuário para excluir!");
            return;
        }
        
        User user = userRepository.getById(editingUserId);
        if (user.getUsername().equals("admin")) {
            JOptionPane.showMessageDialog(this, "Não é possível excluir o usuário admin!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Excluir usuário: " + user.getUsername() + "?", 
            "Confirmar", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            userRepository.delete(user);
            JOptionPane.showMessageDialog(this, "Usuário excluído!");
            clearFields();
            loadUsers();
            loadAuditLogs();
        }
    }
    
    private void clearFields() {
        editingUserId = null;
        usernameField.setText("");
        passwordField.setText("");
        roleCombo.setSelectedIndex(0);
    }
    
    // Classes de ação com nomes específicos
    private class SalvarUsuarioClique implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            saveUser();
        }
    }
    
    private class ExcluirUsuarioClique implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            deleteUser();
        }
    }
    
    private class LimparUsuarioClique implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            clearFields();
        }
    }
    
    private class FecharUsuarioClique implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }
    
    private class TabelaUsuarioClique extends java.awt.event.MouseAdapter {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            int row = userTable.getSelectedRow();
            if (row >= 0) {
                loadUserToForm(row);
            }
        }
    }
    
    private class AtualizarLogsClique implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            loadAuditLogs();
        }
    }
}
