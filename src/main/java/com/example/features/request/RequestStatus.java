package com.example.features.request;

public enum RequestStatus {
    STATUS1("Em Aberto", 1),
    STATUS2("Em produção", 2),
    STATUS3("Concluído", 3),
    STATUS4("Entregue", 4),
    STATUS5("Cancelado", 5);

    private final String name;
    private final int number;

    private RequestStatus(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return this.name;
    }

    public int getNumber() {
        return this.number;
    }
}
