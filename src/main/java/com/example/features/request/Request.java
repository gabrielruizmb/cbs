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

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String description;

    public Client getClient() {
        return this.client;
    }

    public boolean setClient(Client client) {
        if (client == null)
            return false;

        this.client = client;
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
