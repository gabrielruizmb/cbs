package com.crisballon.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import com.crisballon.features.order.Order;
import com.crisballon.features.order.OrderRepository;
import com.crisballon.features.order.OrderItem;
import com.crisballon.features.order.OrderItemRepository;
import com.crisballon.features.client.Client;
import com.crisballon.features.client.ClientRepository;
import com.crisballon.features.product.Product;
import com.crisballon.features.product.ProductRepository;

public class RequestWindow extends JFrame {
    private OrderRepository orderRepository;
    private ClientRepository clientRepository;
    private ProductRepository productRepository;
    private OrderItemRepository orderItemRepository;
    private JTable requestTable, itemsTable;
    private DefaultTableModel tableModel, itemsModel;
    private JComboBox<Client> clientCombo;
    private JComboBox<Product> productCombo;
    private JTextField descriptionField, deliveryFeeField, quantityField;
    private JCheckBox confirmedCheck;
    private Long editingOrderId = null;
    
    public RequestWindow(JFrame parent) {
        try {
            this.orderRepository = new OrderRepository();
            this.clientRepository = new ClientRepository();
            this.productRepository = new ProductRepository();
            this.orderItemRepository = new OrderItemRepository();
            initializeComponents();
            loadRequests();
            loadClients();
            loadProducts();
            setLocationRelativeTo(parent);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent, "Erro ao abrir janela de pedidos: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void initializeComponents() {
        setTitle("Pedidos");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
        
        // Cabeçalho
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.BLACK);
        JLabel titleLabel = new JLabel("Gerenciar Pedidos");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        
        // Painel do formulário
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder("Dados do Pedido"));
        
        formPanel.add(new JLabel("Cliente:"));
        clientCombo = new JComboBox<>();
        formPanel.add(clientCombo);
        
        formPanel.add(new JLabel("Descrição:"));
        descriptionField = new JTextField();
        formPanel.add(descriptionField);
        
        formPanel.add(new JLabel("Taxa de Entrega:"));
        deliveryFeeField = new JTextField("0");
        formPanel.add(deliveryFeeField);
        
        formPanel.add(new JLabel("Confirmado:"));
        confirmedCheck = new JCheckBox();
        formPanel.add(confirmedCheck);
        
        // Painel de itens
        JPanel itemsPanel = new JPanel(new BorderLayout());
        itemsPanel.setBackground(Color.WHITE);
        itemsPanel.setBorder(BorderFactory.createTitledBorder("Itens do Pedido"));
        
        // Formulário de itens
        JPanel itemFormPanel = new JPanel(new FlowLayout());
        itemFormPanel.setBackground(Color.WHITE);
        
        itemFormPanel.add(new JLabel("Produto:"));
        productCombo = new JComboBox<>();
        itemFormPanel.add(productCombo);
        
        itemFormPanel.add(new JLabel("Qtd:"));
        quantityField = new JTextField("1", 3);
        itemFormPanel.add(quantityField);
        
        JButton addItemButton = new JButton("Adicionar");
        addItemButton.setBackground(Color.BLACK);
        addItemButton.setForeground(Color.WHITE);
        addItemButton.addActionListener(e -> addItem());
        itemFormPanel.add(addItemButton);
        
        JButton removeItemButton = new JButton("Remover");
        removeItemButton.setBackground(Color.RED);
        removeItemButton.setForeground(Color.WHITE);
        removeItemButton.addActionListener(e -> removeItem());
        itemFormPanel.add(removeItemButton);
        
        itemsPanel.add(itemFormPanel, BorderLayout.NORTH);
        
        // Tabela de itens
        createItemsTable();
        itemsPanel.add(new JScrollPane(itemsTable), BorderLayout.CENTER);
        
        // Botões
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);
        
        JButton saveButton = new JButton("Salvar");
        saveButton.setBackground(Color.BLACK);
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(new SalvarPedidoClique());
        
        JButton deleteButton = new JButton("Excluir");
        deleteButton.setBackground(Color.BLACK);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener(new ExcluirPedidoClique());
        
        JButton clearButton = new JButton("Limpar");
        clearButton.setBackground(Color.LIGHT_GRAY);
        clearButton.setForeground(Color.BLACK);
        clearButton.addActionListener(new LimparPedidoClique());
        
        JButton closeButton = new JButton("Fechar");
        closeButton.setBackground(Color.LIGHT_GRAY);
        closeButton.setForeground(Color.BLACK);
        closeButton.addActionListener(new FecharPedidoClique());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(closeButton);
        
        // Layout
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.NORTH);
        topPanel.add(itemsPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        
        // Tabela de pedidos
        createTable();
        mainPanel.add(new JScrollPane(requestTable), BorderLayout.CENTER);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Clique na tabela
        requestTable.addMouseListener(new TabelaPedidoClique());
    }
    
    private void createTable() {
        String[] columns = {"ID", "Cliente", "Descrição", "Total", "Confirmado"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        requestTable = new JTable(tableModel);
        requestTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        requestTable.getTableHeader().setBackground(Color.LIGHT_GRAY);
    }
    
    private void createItemsTable() {
        String[] columns = {"Produto", "Qtd", "Preço Unit.", "Total"};
        itemsModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        itemsTable = new JTable(itemsModel);
        itemsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemsTable.getTableHeader().setBackground(Color.LIGHT_GRAY);
    }
    
    private void addItem() {
        if (productCombo.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Selecione um produto!");
            return;
        }
        
        Product product = (Product) productCombo.getSelectedItem();
        int quantity;
        
        try {
            quantity = Integer.parseInt(quantityField.getText().trim());
            if (quantity <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantidade deve ser um número positivo!");
            return;
        }
        
        Object[] row = {
            product.getName(),
            quantity,
            "R$ " + product.getPrice(),
            "R$ " + String.format("%.2f", product.getPrice() * quantity)
        };
        
        itemsModel.addRow(row);
        quantityField.setText("1");
    }
    
    private void removeItem() {
        int selectedRow = itemsTable.getSelectedRow();
        if (selectedRow >= 0) {
            itemsModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um item para remover!");
        }
    }
    
    private void loadRequests() {
        tableModel.setRowCount(0);
        List<Order> orders = orderRepository.getAll();
        
        for (Order order : orders) {
            String clientName = "N/A";
            
            if (order.getClientId() != null) {
                try {
                    Client client = clientRepository.getById(order.getClientId());
                    if (client != null) clientName = client.getName();
                } catch (Exception e) {}
            }
            
            double total = calculateOrderTotal(order);
            
            Object[] row = {
                order.getId(),
                clientName,
                order.getDescription(),
                "R$ " + String.format("%.2f", total),
                order.getConfirmed() ? "Sim" : "Não"
            };
            tableModel.addRow(row);
        }
    }
    
    private double calculateOrderTotal(Order order) {
        double itemsTotal = 0.0;
        if (order.getId() != null) {
            List<OrderItem> items = orderItemRepository.getByOrderId(order.getId());
            itemsTotal = items.stream().mapToDouble(OrderItem::getTotalPrice).sum();
        }
        
        double deliveryFee = order.getDeliveryFee() != null ? order.getDeliveryFee() : 0.0;
        return itemsTotal + deliveryFee;
    }
    
    private void loadClients() {
        clientCombo.removeAllItems();
        List<Client> clients = clientRepository.getAll();
        for (Client client : clients) {
            clientCombo.addItem(client);
        }
    }
    
    private void loadProducts() {
        productCombo.removeAllItems();
        List<Product> products = productRepository.getAll();
        for (Product product : products) {
            productCombo.addItem(product);
        }
    }
    
    private void loadRequestToForm(int row) {
        Long orderId = (Long) tableModel.getValueAt(row, 0);
        Order order = orderRepository.getById(orderId);
        
        if (order != null) {
            editingOrderId = orderId;
            
            // Carrega cliente
            if (order.getClientId() != null) {
                for (int i = 0; i < clientCombo.getItemCount(); i++) {
                    Client client = clientCombo.getItemAt(i);
                    if (client.getId().equals(order.getClientId())) {
                        clientCombo.setSelectedIndex(i);
                        break;
                    }
                }
            }
            
            descriptionField.setText(order.getDescription());
            deliveryFeeField.setText(order.getDeliveryFee() != null ? order.getDeliveryFee().toString() : "0");
            confirmedCheck.setSelected(order.getConfirmed());
            
            // Carrega itens
            loadOrderItems(orderId);
        }
    }
    
    private void loadOrderItems(Long orderId) {
        itemsModel.setRowCount(0);
        List<OrderItem> items = orderItemRepository.getByOrderId(orderId);
        
        for (OrderItem item : items) {
            try {
                Product product = productRepository.getById(item.getProductId());
                if (product != null) {
                    Object[] row = {
                        product.getName(),
                        item.getQuantity(),
                        "R$ " + item.getUnitPrice(),
                        "R$ " + String.format("%.2f", item.getTotalPrice())
                    };
                    itemsModel.addRow(row);
                }
            } catch (Exception e) {}
        }
    }
    
    private void saveRequest() {
        if (clientCombo.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente!");
            return;
        }
        
        if (descriptionField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Descrição é obrigatória!");
            return;
        }
        
        if (itemsModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Adicione pelo menos um item!");
            return;
        }
        
        Order order;
        if (editingOrderId != null) {
            order = orderRepository.getById(editingOrderId);
            if (order == null) {
                order = new Order();
            }
        } else {
            order = new Order();
        }
        
        Client selectedClient = (Client) clientCombo.getSelectedItem();
        order.setClientId(selectedClient.getId());
        order.setDescription(descriptionField.getText().trim());
        order.setStatus("Pendente");
        order.setPrice(0.0);
        
        try {
            String deliveryText = deliveryFeeField.getText().trim().replace(",", ".");
            if (!deliveryText.isEmpty()) {
                order.setDeliveryFee(Double.parseDouble(deliveryText));
            } else {
                order.setDeliveryFee(0.0);
            }
        } catch (NumberFormatException e) {
            order.setDeliveryFee(0.0);
        }
        
        order.setConfirmed(confirmedCheck.isSelected());
        
        try {
            if (editingOrderId != null) {
                orderRepository.update(order);
                orderItemRepository.deleteByOrderId(editingOrderId);
            } else {
                orderRepository.create(order);
                editingOrderId = order.getId();
            }
            
            // Salva itens
            for (int i = 0; i < itemsModel.getRowCount(); i++) {
                String productName = (String) itemsModel.getValueAt(i, 0);
                Integer quantity = (Integer) itemsModel.getValueAt(i, 1);
                String priceStr = ((String) itemsModel.getValueAt(i, 2)).replace("R$ ", "");
                Double unitPrice = Double.parseDouble(priceStr);
                
                // Busca produto pelo nome
                Product product = null;
                for (int j = 0; j < productCombo.getItemCount(); j++) {
                    Product p = productCombo.getItemAt(j);
                    if (p.getName().equals(productName)) {
                        product = p;
                        break;
                    }
                }
                
                if (product != null) {
                    OrderItem item = new OrderItem(editingOrderId, product.getId(), quantity, unitPrice);
                    orderItemRepository.create(item);
                }
            }
            
            JOptionPane.showMessageDialog(this, "Pedido salvo!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar pedido: " + e.getMessage());
            return;
        }
        
        clearFields();
        loadRequests();
    }
    
    private void deleteRequest() {
        if (editingOrderId == null) {
            JOptionPane.showMessageDialog(this, "Selecione um pedido para excluir!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Excluir pedido?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Order order = orderRepository.getById(editingOrderId);
            if (order != null) {
                orderItemRepository.deleteByOrderId(editingOrderId);
                orderRepository.delete(order);
                JOptionPane.showMessageDialog(this, "Pedido excluído!");
                clearFields();
                loadRequests();
            }
        }
    }
    
    private void clearFields() {
        editingOrderId = null;
        clientCombo.setSelectedIndex(-1);
        descriptionField.setText("");
        deliveryFeeField.setText("0");
        confirmedCheck.setSelected(false);
        itemsModel.setRowCount(0);
    }
    
    // Classes de ação
    private class SalvarPedidoClique implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            saveRequest();
        }
    }
    
    private class ExcluirPedidoClique implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            deleteRequest();
        }
    }
    
    private class LimparPedidoClique implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            clearFields();
        }
    }
    
    private class FecharPedidoClique implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }
    
    private class TabelaPedidoClique extends java.awt.event.MouseAdapter {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            int row = requestTable.getSelectedRow();
            if (row >= 0) {
                loadRequestToForm(row);
            }
        }
    }
}
