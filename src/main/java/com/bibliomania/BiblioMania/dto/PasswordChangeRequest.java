package com.bibliomania.BiblioMania.dto;

public class PasswordChangeRequest {

    private String passwordActual;
    private String passwordNueva;

    // Constructor vacío
    public PasswordChangeRequest() {}

    // Getters y Setters
    public String getPasswordActual() {
        return passwordActual;
    }

    public void setPasswordActual(String passwordActual) {
        this.passwordActual = passwordActual;
    }

    public String getPasswordNueva() {
        return passwordNueva;
    }

    public void setPasswordNueva(String passwordNueva) {
        this.passwordNueva = passwordNueva;
    }
}