package com.example.features.request;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.example.features.client.Client;

@Entity
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(
        name = "client_id", 
        referencedColumnName = "id", 
        nullable = false
    )
    private Client client;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String description;

    public Long getId() {
        return this.id;
    }

    public Client getClient() {
        return this.client;
    }

    public boolean setClient(Client client) {
        if (client == null)
            return false;

        this.client = client;
        return true;
    }

    public String getStatus() {
        return this.status;
    }

    public boolean setStatus(String status) {
        if (status.isBlank())
            return false;

        this.status = status;
        return true;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean setDescription(String description) {
        if (description.isBlank())
            return false;

        this.description = description;
        return true;
    }

    public Request() {
    }

    public Request(Long id, Client client, String status, String description) {
        this.id = id;
        this.client = client;
        this.status = status;
        this.description = description;
    }
}
