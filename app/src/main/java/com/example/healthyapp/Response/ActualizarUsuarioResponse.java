package com.example.healthyapp.Response;

import com.example.healthyapp.Model.UserObjects.Token;
import com.example.healthyapp.Model.UserObjects.User;
import com.google.gson.annotations.SerializedName;

public class ActualizarUsuarioResponse {
    public ActualizarUsuarioResponse(Throwable exception) {
        this.message = exception.getMessage();
    }

    private String message;

    public String getMessage() {
        return message;
    }

    private User data;

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
