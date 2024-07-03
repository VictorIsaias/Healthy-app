package com.example.healthyapp.Response;

import com.example.healthyapp.Model.UserObjects.Token;
import com.example.healthyapp.Model.UserObjects.User;
import com.example.healthyapp.Model.UserObjects.UserLogin;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    public LoginResponse(Throwable exception) {
        this.message = exception.getMessage();
    }

    private String message;

    public String getMessage() {
        return message;
    }

    @SerializedName("data")
    private LoginResponse.data data;

    public LoginResponse.data getData() {
        return data;
    }

    public void setData(LoginResponse.data data) {
        this.data = data;
    }

    public class data{

        @SerializedName("token")
        private Token token;
        @SerializedName("user")
        private User user;


        public Token getToken() {
            return this.token;
        }

        public void setToken(Token token) {
            this.token = token;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User usuario) {
            this.user = usuario;
        }
    }
}
