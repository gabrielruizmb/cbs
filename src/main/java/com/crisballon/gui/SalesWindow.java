package com.crisballon.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import com.crisballon.features.order.Order;
import com.crisballon.features.order.OrderRepository;
import com.crisballon.features.client.Client;
import com.crisballon.features.client.ClientRepository;

/**
 * Janela para gerenciar vendas - confirmar e cancelar pedidos
 */
public class SalesWindow extends JFrame {
    private OrderRepository orderRepository;
    private ClientRepository clientRepository;
    private JTable pendingTable, confirmedTable;
    private DefaultTableModel pendingModel, confirmedModel;
    private JLabel totalLabel;
    
    public SalesWindow(JFrame parent) {
        this.orderRepository = new OrderRepository();
        this.clientRepository = new ClientRepository();
        initializeComponents();
        loadSalesData();
        setLocationRelativeTo(parent);
    }
    
    // Configura a janela de vendas
    private void initializeComponents() {
        setTitle("Vendas");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
        
        // Cabeçalho
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.BLACK);
        JLabel titleLabel = new JLabel("Gerenciar Vendas");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Abas para pedidos pendentes e vendas confirmadas
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Pedidos Pendentes", createPendingPanel());
        tabbedPane.addTab("Vendas Confirmadas", createConfirmedPanel());
        add(tabbedPane, BorderLayout.CENTER);
        
        // Botão fechar
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBackground(Color.WHITE);
        JButton closeButton = new JButton("Fechar");
        closeButton.setBackground(Color.LIGHT_GRAY);
        closeButton.addActionListener(e -> dispose());
        bottomPanel.add(closeButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    // Cria painel dos pedidos pendentes
    private JPanel createPendingPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Tabela de pedidos pendentes
        String[] columns = {"ID", "Cliente", "Descrição", "Preço"};
        pendingModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        pendingTable = new JTable(pendingModel);
        pendingTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        pendingTable.getTableHeader().setBackground(Color.LIGHT_GRAY);
        
        JScrollPane scrollPane = new JScrollPane(pendingTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Pedidos Pendentes"));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Botões confirmar e excluir venda
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);
        
        JButton confirmButton = new JButton("Confirmar Venda");
        confirmButton.setBackground(Color.BLACK);
        confirmButton.setForeground(Color.WHITE);
        confirmButton.addActionListener(e -> confirmSale());
        buttonPanel.add(confirmButton);
        
        JButton deleteButton = new JButton("Excluir Venda");
        deleteButton.setBackground(Color.RED);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener(e -> deleteSale());
        buttonPanel.add(deleteButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    // Cria painel das vendas confirmadas
    private JPanel createConfirmedPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Tabela de vendas confirmadas
        String[] columns = {"ID", "Cliente", "Descrição", "Preço"};
        confirmedModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        confirmedTable = new JTable(confirmedModel);
        confirmedTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        confirmedTable.getTableHeader().setBackground(Color.LIGHT_GRAY);
        
        JScrollPane scrollPane = new JScrollPane(confirmedTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Vendas Confirmadas"));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Painel com total e botão cancelar
        JPanel totalPanel = new JPanel(new FlowLayout());
        totalPanel.setBackground(Color.WHITE);
        
        totalLabel = new JLabel("Total de Vendas: R$ 0,00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalPanel.add(totalLabel);
        
        JButton cancelButton = new JButton("Cancelar Venda");
        cancelButton.setBackground(Color.ORANGE);
        cancelButton.setForeground(Color.BLACK);
        cancelButton.addActionListener(e -> unconfirmSale());
        totalPanel.add(cancelButton);
        
        JButton deleteButton = new JButton("Excluir Venda");
        deleteButton.setBackground(Color.RED);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener(e -> deleteConfirmedSale());
        totalPanel.add(deleteButton);
        
        panel.add(totalPanel, BorderLayout.SOUTH);
        return panel;
    }
    
    // Carrega dados das vendas (pendentes e confirmadas)
    private void loadSalesData() {
        loadPendingSales();
        loadConfirmedSales();
    }
    
    // Carrega pedidos pendentes na tabela
    private void loadPendingSales() {
        pendingModel.setRowCount(0);
        List<Order> pendingOrders = orderRepository.getPendingOrders();
        
        for (Order order : pendingOrders) {
            String clientName = getClientName(order.getClientId());
            Object[] row = {
                order.getId(),
                clientName,
                order.getDescription(),
                "R$ " + String.format("%.2f", order.getTotalPrice())
            };
            pendingModel.addRow(row);
        }
    }
    
    // Carrega vendas confirmadas e calcula total
    private void loadConfirmedSales() {
        confirmedModel.setRowCount(0);
        List<Order> confirmedSales = orderRepository.getConfirmedSales();
        
        double total = 0.0;
        for (Order order : confirmedSales) {
            String clientName = getClientName(order.getClientId());
            Object[] row = {
                order.getId(),
                clientName,
                order.getDescription(),
                "R$ " + String.format("%.2f", order.getTotalPrice())
            };
            confirmedModel.addRow(row);
            total += order.getTotalPrice();
        }
        
        totalLabel.setText(String.format("Total de Vendas: R$ %.2f", total));
    }
    
    // Busca nome do cliente pelo ID
    private String getClientName(Long clientId) {
        try {
            Client client = clientRepository.getById(clientId);
            return (client != null) ? client.getName() : "N/A";
        } catch (Exception e) {
            return "N/A";
        }
    }
    
    // Confirma uma venda selecionada
    private void confirmSale() {
        int selectedRow = pendingTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um pedido para confirmar!");
            return;
        }
        
        Long orderId = (Long) pendingModel.getValueAt(selectedRow, 0);
        Order order = orderRepository.getById(orderId);
        
        if (order != null) {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Confirmar venda do pedido: " + order.getDescription() + "?",
                "Confirmar Venda", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                order.setConfirmed(true);
                orderRepository.update(order);
                JOptionPane.showMessageDialog(this, "Venda confirmada!");
                loadSalesData();
            }
        }
    }
    
    // Cancela uma venda confirmada
    private void unconfirmSale() {
        int selectedRow = confirmedTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma venda para cancelar!");
            return;
        }
        
        Long orderId = (Long) confirmedModel.getValueAt(selectedRow, 0);
        Order order = orderRepository.getById(orderId);
        
        if (order != null) {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Cancelar venda do pedido: " + order.getDescription() + "?",
                "Cancelar Venda", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                order.setConfirmed(false);
                orderRepository.update(order);
                JOptionPane.showMessageDialog(this, "Venda cancelada!");
                loadSalesData();
            }
        }
    }
    
    // Exclui uma venda pendente
    private void deleteSale() {
        int selectedRow = pendingTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma venda para excluir!");
            return;
        }
        
        Long orderId = (Long) pendingModel.getValueAt(selectedRow, 0);
        Order order = orderRepository.getById(orderId);
        
        if (order != null) {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Excluir permanentemente o pedido: " + order.getDescription() + "?",
                "Excluir Venda", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                orderRepository.delete(order);
                JOptionPane.showMessageDialog(this, "Venda excluída!");
                loadSalesData();
            }
        }
    }
    
    // Exclui uma venda confirmada
    private void deleteConfirmedSale() {
        int selectedRow = confirmedTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma venda para excluir!");
            return;
        }
        
        Long orderId = (Long) confirmedModel.getValueAt(selectedRow, 0);
        Order order = orderRepository.getById(orderId);
        
        if (order != null) {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Excluir permanentemente o pedido: " + order.getDescription() + "?",
                "Excluir Venda", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                orderRepository.delete(order);
                JOptionPane.showMessageDialog(this, "Venda excluída!");
                loadSalesData();
            }
        }
    }
}
