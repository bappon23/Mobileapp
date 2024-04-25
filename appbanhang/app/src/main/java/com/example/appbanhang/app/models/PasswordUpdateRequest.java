package com.example.appbanhang.app.models;

public class PasswordUpdateRequest {
    private String newPassword;

    public PasswordUpdateRequest(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}