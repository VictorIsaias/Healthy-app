package com.example.healthyapp.Model.UserObjects;

public class UserLogin {
    private Integer id;
    private String name;
    private String lastname;
    private String email;

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

    public UserLogin(Integer id, String name, String lastname, String email, String email_verified_at, String verification_code, String password, String created_at, String updated_at) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.email_verified_at = email_verified_at;
        this.verification_code = verification_code;
        this.password = password;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

}
