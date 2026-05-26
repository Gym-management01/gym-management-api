package com.example.demo.model;

public enum UserRole {
    ADMIN("ADMIN"),
    GERENTE("GERENTE"),
    INSTRUTOR("INSTRUTOR"),
    RECEPCIONISTA("RECEPCIONISTA");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
