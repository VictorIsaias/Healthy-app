package com.example.healthyapp.Model.UserObjects;

public class UsuarioRegistro {
    private String name;
    private String lastname;
    private String email;
    private String password;

    public UsuarioRegistro(String name, String lastname, String email, String password) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    private String oldPassword;

    public UsuarioRegistro(String oldPassword, String newPassword) {
        this.oldPassword=oldPassword;
        this.newPassword=newPassword;
    }

    public String getoldPassword() {
        return oldPassword;
    }

    public void setoldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getnewPassword() {
        return newPassword;
    }

    public void setnewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    private String newPassword;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UsuarioRegistro(){

    }

}