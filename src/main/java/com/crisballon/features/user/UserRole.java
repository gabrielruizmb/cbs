package com.crisballon.features.user;

public enum UserRole {
    ADMIN("Admin"),
    USER("Usuário Padrão");

    private final String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
