package com.example.healthyapp.Model.UserObjects;

import com.example.healthyapp.Model.DeviceObjects.Configuration;
import com.example.healthyapp.Model.DeviceObjects.Dispositivo;

import java.util.List;

public class User {
    private Integer id;

    public User(Integer id, String email,List<Dispositivo> dispositivos) {
        this.id = id;
        this.email = email;
        this.dispositivo = dispositivos;
    }

    private String name;
    private String lastname;
    private String email;
    private List<Configuration> configurations;


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

    private String oldPassword;

    public User() {

    }

    public User(String oldPassword, String newPassword) {
        this.oldPassword=oldPassword;
        this.newPassword=newPassword;
    }

    public List<Configuration> getConfigurations() {
        return configurations;
    }

    private List<Dispositivo> dispositivo;

    public List<Dispositivo> getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(List<Dispositivo> dispositivo) {
        this.dispositivo = dispositivo;
    }

    private String email_verified_at;

    public String getEmail_verified_at() {
        return email_verified_at;
    }

    public void setEmail_verified_at(String email_verified_at) {
        this.email_verified_at = email_verified_at;
    }

    public String getVerification_code() {
        return verification_code;
    }

    public void setVerification_code(String verification_code) {
        this.verification_code = verification_code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    private String verification_code;

    private String password;

    private String created_at;
    private String updated_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public User(String name, String lastname, String email, String email_verified_at, String verification_code, String password, List<Dispositivo> dispositivo) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.email_verified_at = email_verified_at;
        this.verification_code = verification_code;
        this.password = password;
        this.dispositivo = dispositivo;
    }

    public User(String name,String lastname,String email){
        this.name = name;
        this.lastname = lastname;
        this.email = email;
    }
}
