package com.example.features.client;

import javax.persistence.*;

@Entity
//client tava com c maiusculo, o postgresql transforma tudo em minusculo mas o hibernate procura o nome exato
@Table(name = "client")
public class Client {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    private String adress; 
    private String secondaryContact;

    
    public Client() {
    }
    
    public Client(Long id, String name, String phone, String adress, 
        String secondaryContact) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.adress = adress;
        this.secondaryContact = secondaryContact;
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public boolean setName(String name) {
        if (name.isBlank())
            return false;

        this.name = name;
        return true;    
    }

    public String getPhone() {
        return phone;
    }

    public boolean setPhone(String phone) {
        if (phone.isBlank())
            return false;

        this.phone = phone;
        return true;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getSecondaryContact() {
        return secondaryContact;
    }

    public void setSecondaryContact(String secondaryContact) {
        this.secondaryContact = secondaryContact;
    }
}
