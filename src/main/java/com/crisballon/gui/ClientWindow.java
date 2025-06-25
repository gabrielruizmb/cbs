package com.crisballon.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import com.crisballon.features.client.Client;
import com.crisballon.features.client.ClientRepository;

public class ClientWindow extends JFrame {
    private ClientRepository clientRepository;
    private JTable clientTable;
    private DefaultTableModel tableModel;
    private JTextField nameField, phoneField, addressField, contactField;
    private Long editingClientId = null;
    
    public ClientWindow(JFrame parent) {
        this.clientRepository = new ClientRepository();
        initializeComponents();
        loadClients();
        setLocationRelativeTo(parent);
    }
    
    private void initializeComponents() {
        setTitle("Clientes");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
        
        // Cabeçalho
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.BLACK);
        JLabel titleLabel = new JLabel("Gerenciar Clientes");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        
        // Painel de formulário
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder("Dados do Cliente"));
        
        formPanel.add(new JLabel("Nome:"));
        nameField = new JTextField();
        formPanel.add(nameField);
        
        formPanel.add(new JLabel("Telefone:"));
        phoneField = new JTextField();
        formPanel.add(phoneField);
        
        formPanel.add(new JLabel("Endereço:"));
        addressField = new JTextField();
        formPanel.add(addressField);
        
        formPanel.add(new JLabel("Contato Secundário:"));
        contactField = new JTextField();
        formPanel.add(contactField);
        
        // Botões
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);
        
        JButton saveButton = new JButton("Salvar");
        saveButton.setBackground(Color.BLACK);
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(new AcaoSalvar());
        
        JButton deleteButton = new JButton("Excluir");
        deleteButton.setBackground(Color.BLACK);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener(new AcaoExcluir());
        
        JButton clearButton = new JButton("Limpar");
        clearButton.setBackground(Color.LIGHT_GRAY);
        clearButton.setForeground(Color.BLACK);
        clearButton.addActionListener(new AcaoLimpar());
        
        JButton closeButton = new JButton("Fechar");
        closeButton.setBackground(Color.LIGHT_GRAY);
        closeButton.setForeground(Color.BLACK);
        closeButton.addActionListener(new AcaoFechar());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(closeButton);
        
        formPanel.add(buttonPanel);
        
        mainPanel.add(formPanel, BorderLayout.NORTH);
        
        // Tabela
        createTable();
        mainPanel.add(new JScrollPane(clientTable), BorderLayout.CENTER);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Clique na tabela
        clientTable.addMouseListener(new AcaoTabelaClique());
    }
    
    private void createTable() {
        String[] columns = {"ID", "Nome", "Telefone"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        clientTable = new JTable(tableModel);
        clientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        clientTable.getTableHeader().setBackground(Color.LIGHT_GRAY);
    }
    
    private void loadClients() {
        tableModel.setRowCount(0);
        List<Client> clients = clientRepository.getAll();
        
        for (Client client : clients) {
            Object[] row = {client.getId(), client.getName(), client.getPhone()};
            tableModel.addRow(row);
        }
    }
    
    private void loadClientToForm(int row) {
        Long clientId = (Long) tableModel.getValueAt(row, 0);
        Client client = clientRepository.getById(clientId);
        
        if (client != null) {
            editingClientId = clientId;
            nameField.setText(client.getName());
            phoneField.setText(client.getPhone());
            addressField.setText(client.getAddress() != null ? client.getAddress() : "");
            contactField.setText(client.getSecondaryContact() != null ? client.getSecondaryContact() : "");
        }
    }
    
    private void saveClient() {
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome é obrigatório!");
            return;
        }
        
        if (phoneField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Telefone é obrigatório!");
            return;
        }
        
        Client client;
        if (editingClientId != null) {
            client = clientRepository.getById(editingClientId);
        } else {
            client = new Client();
        }
        
        client.setName(nameField.getText().trim());
        client.setPhone(phoneField.getText().trim());
        client.setAddress(addressField.getText().trim());
        client.setSecondaryContact(contactField.getText().trim());
        
        if (editingClientId != null) {
            clientRepository.update(client);
            JOptionPane.showMessageDialog(this, "Cliente atualizado!");
        } else {
            clientRepository.create(client);
            JOptionPane.showMessageDialog(this, "Cliente salvo!");
        }
        
        clearFields();
        loadClients();
    }
    
    private void deleteClient() {
        if (editingClientId == null) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para excluir!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Excluir cliente?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Client client = clientRepository.getById(editingClientId);
            if (client != null) {
                clientRepository.delete(client);
                JOptionPane.showMessageDialog(this, "Cliente excluído!");
                clearFields();
                loadClients();
            }
        }
    }
    
    private void clearFields() {
        editingClientId = null;
        nameField.setText("");
        phoneField.setText("");
        addressField.setText("");
        contactField.setText("");
    }
    
    // Classes de ação
    private class AcaoSalvar implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            saveClient();
        }
    }
    
    private class AcaoExcluir implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            deleteClient();
        }
    }
    
    private class AcaoLimpar implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            clearFields();
        }
    }
    
    private class AcaoFechar implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }
    
    private class AcaoTabelaClique extends java.awt.event.MouseAdapter {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            int row = clientTable.getSelectedRow();
            if (row >= 0) {
                loadClientToForm(row);
            }
        }
    }
}
