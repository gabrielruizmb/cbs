package com.crisballon.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import com.crisballon.features.product.Product;
import com.crisballon.features.product.ProductRepository;

public class ProductWindow extends JFrame {
    private ProductRepository productRepository;
    private JTable productTable;
    private DefaultTableModel tableModel;
    private JTextField nameField, priceField, descriptionField;
    private Long editingProductId = null;
    
    public ProductWindow(JFrame parent) {
        this.productRepository = new ProductRepository();
        initializeComponents();
        loadProducts();
        setLocationRelativeTo(parent);
    }
    
    private void initializeComponents() {
        setTitle("Produtos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
        
        // Cabeçalho
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.BLACK);
        JLabel titleLabel = new JLabel("Gerenciar Produtos");
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
        formPanel.setBorder(BorderFactory.createTitledBorder("Dados do Produto"));
        
        formPanel.add(new JLabel("Nome:"));
        nameField = new JTextField();
        formPanel.add(nameField);
        
        formPanel.add(new JLabel("Preço:"));
        priceField = new JTextField();
        formPanel.add(priceField);
        
        formPanel.add(new JLabel("Descrição:"));
        descriptionField = new JTextField();
        formPanel.add(descriptionField);
        
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
        mainPanel.add(new JScrollPane(productTable), BorderLayout.CENTER);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Clique na tabela
        productTable.addMouseListener(new AcaoTabelaClique());
    }
    
    private void createTable() {
        String[] columns = {"ID", "Nome", "Preço", "Descrição"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        productTable = new JTable(tableModel);
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productTable.getTableHeader().setBackground(Color.LIGHT_GRAY);
    }
    
    private void loadProducts() {
        tableModel.setRowCount(0);
        List<Product> products = productRepository.getAll();
        
        for (Product product : products) {
            Object[] row = {
                product.getId(),
                product.getName(),
                "R$ " + product.getPrice(),
                product.getDescription()
            };
            tableModel.addRow(row);
        }
    }
    
    private void loadProductToForm(int row) {
        Long productId = (Long) tableModel.getValueAt(row, 0);
        Product product = productRepository.getById(productId);
        
        if (product != null) {
            editingProductId = productId;
            nameField.setText(product.getName());
            priceField.setText(product.getPrice().toString());
            descriptionField.setText(product.getDescription() != null ? product.getDescription() : "");
        }
    }
    
    private void saveProduct() {
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome é obrigatório!");
            return;
        }
        
        if (priceField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preço é obrigatório!");
            return;
        }
        
        Product product;
        if (editingProductId != null) {
            product = productRepository.getById(editingProductId);
        } else {
            product = new Product();
        }
        
        product.setName(nameField.getText().trim());
        
        try {
            String priceText = priceField.getText().trim().replace(",", ".");
            product.setPrice(Double.parseDouble(priceText));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Preço inválido! Use formato: 0.99 ou 0,99");
            return;
        }
        
        product.setDescription(descriptionField.getText().trim());
        
        if (editingProductId != null) {
            productRepository.update(product);
            JOptionPane.showMessageDialog(this, "Produto atualizado!");
        } else {
            productRepository.create(product);
            JOptionPane.showMessageDialog(this, "Produto salvo!");
        }
        
        clearFields();
        loadProducts();
    }
    
    private void deleteProduct() {
        if (editingProductId == null) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para excluir!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Excluir produto?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Product product = productRepository.getById(editingProductId);
            if (product != null) {
                productRepository.delete(product);
                JOptionPane.showMessageDialog(this, "Produto excluído!");
                clearFields();
                loadProducts();
            }
        }
    }
    
    private void clearFields() {
        editingProductId = null;
        nameField.setText("");
        priceField.setText("");
        descriptionField.setText("");
    }
    
    // Classes de ação 
    private class AcaoSalvar implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            saveProduct();
        }
    }
    
    private class AcaoExcluir implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            deleteProduct();
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
            int row = productTable.getSelectedRow();
            if (row >= 0) {
                loadProductToForm(row);
            }
        }
    }
}
