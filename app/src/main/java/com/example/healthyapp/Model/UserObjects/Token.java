package com.example.healthyapp.Model.UserObjects;

public class Token {
    private String type;
    private String expires_at;
    private String token;

    public Token(String type, String expires_at, String token) {
        this.type = type;
        this.expires_at = expires_at;
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExpires_at() {
        return expires_at;
    }

    public void setExpires_at(String expires_at) {
        this.expires_at = expires_at;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
