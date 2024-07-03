package com.example.healthyapp.Model.UserObjects;

public class PassRecoveryRequest {
    private String email;

    public PassRecoveryRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
