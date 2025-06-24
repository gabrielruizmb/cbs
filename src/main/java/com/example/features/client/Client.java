package com.example.features.client;

import javax.persistence.*;

@Entity
@Table(name = "clients")
public class Client {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    private String address;
    private String secondaryContact;

    // Construtor padrão
    public Client() {}

    // Construtor com parâmetros
    public Client(String name, String phone, String address, String secondaryContact) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.secondaryContact = secondaryContact;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getSecondaryContact() { return secondaryContact; }
    public void setSecondaryContact(String secondaryContact) { 
        this.secondaryContact = secondaryContact; 
    }
}