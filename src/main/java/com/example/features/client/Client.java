package com.example.features.client;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "clients", 
       indexes = {@Index(name = "idx_client_name", columnList = "name"),
                  @Index(name = "idx_client_phone", columnList = "phone", unique = true)})
public class Client {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "phone", nullable = false, length = 15, unique = true)
    private String phone;

    @Column(name = "address", length = 200)
    private String address;

    @Column(name = "secondary_contact", length = 100)
    private String secondaryContact;

    @Version
    private Long version;

    // Construtores
    public Client() {
        // Construtor padrão necessário para JPA
    }

    public Client(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public Client(String name, String phone, String address, String secondaryContact) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.secondaryContact = secondaryContact;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public boolean setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        this.name = name.trim();
        return true;
    }

    public String getPhone() {
        return phone;
    }

    public boolean setPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        
        // Remove todos os caracteres não numéricos
        String cleanedPhone = phone.replaceAll("[^0-9]", "");
        
        // Valida se tem 10 ou 11 dígitos (DD + número)
        if (cleanedPhone.length() < 10 || cleanedPhone.length() > 11) {
            return false;
        }
        
        this.phone = cleanedPhone;
        return true;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address != null ? address.trim() : null;
    }

    public String getSecondaryContact() {
        return secondaryContact;
    }

    public void setSecondaryContact(String secondaryContact) {
        this.secondaryContact = secondaryContact != null ? secondaryContact.trim() : null;
    }

    public Long getVersion() {
        return version;
    }

    // Métodos utilitários
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id) && 
               Objects.equals(phone, client.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, phone);
    }

    @Override
    public String toString() {
        return "Client{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", phone='" + phone + '\'' +
               ", address='" + address + '\'' +
               ", secondaryContact='" + secondaryContact + '\'' +
               '}';
    }

    // Método para formatação de exibição
    public String getFormattedPhone() {
        if (phone == null || phone.length() < 10) return phone;
        
        // Formata como (XX) XXXX-XXXX ou (XX) XXXXX-XXXX
        return String.format("(%s) %s-%s", 
            phone.substring(0, 2),
            phone.substring(2, phone.length() - 4),
            phone.substring(phone.length() - 4));
    }
}