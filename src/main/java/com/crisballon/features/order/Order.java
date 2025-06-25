package com.crisballon.features.order;

import javax.persistence.*;

/**
 * Classe que representa um pedido no sistema
 * Contém informações do cliente, descrição, preço e status
 */
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String description;

    @Column
    private Double price = 0.0;

    @Column
    private Boolean confirmed = false;
    
    @Column
    private Double deliveryFee = 0.0;
    
    // Construtor padrão
    public Order() {}

    public Long getId() {
        return this.id;
    }

    public Long getClientId() {
        return this.clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getConfirmed() {
        return this.confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }
    

    
    public Double getDeliveryFee() {
        return this.deliveryFee;
    }
    
    public void setDeliveryFee(Double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }
    
    // Calcula o preço total do pedido (soma dos itens ou preço base + taxa de entrega)
    public Double getTotalPrice() {
        double itemsTotal = getItemsTotal();
        double basePrice = (price != null) ? price : 0.0;
        double deliveryValue = (deliveryFee != null) ? deliveryFee : 0.0;
        
        // Se não há itens, usa o preço base
        if (itemsTotal == 0.0 && basePrice > 0.0) {
            return basePrice + deliveryValue;
        }
        
        return itemsTotal + deliveryValue;
    }
    
    // Calcula o total dos itens do pedido
    public Double getItemsTotal() {
        try {
            OrderItemRepository itemRepo = new OrderItemRepository();
            return itemRepo.getByOrderId(this.id).stream()
                    .mapToDouble(OrderItem::getTotalPrice)
                    .sum();
        } catch (Exception e) {
            return 0.0;
        }
    }
    
    // Construtor com parâmetros
    public Order(Long id, Long clientId, String status, String description) {
        this.id = id;
        this.clientId = clientId;
        this.status = status;
        this.description = description;
        this.price = 0.0;
        this.deliveryFee = 0.0;
        this.confirmed = false;
    }
}
