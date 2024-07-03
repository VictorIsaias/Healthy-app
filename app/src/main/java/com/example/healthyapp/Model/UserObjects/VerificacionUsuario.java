package com.example.healthyapp.Model.UserObjects;

public class VerificacionUsuario {
    private String user_email;
    private String password;
    private String verification_code;

    public VerificacionUsuario(String user_email, String password, String verification_code) {
        this.user_email = user_email;
        this.password = password;
        this.verification_code = verification_code;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerification_code() {
        return verification_code;
    }

    public void setVerification_code(String verification_code) {
        this.verification_code = verification_code;
    }
}
