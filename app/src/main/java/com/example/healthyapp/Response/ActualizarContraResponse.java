package com.example.healthyapp.Response;
import com.example.healthyapp.Model.UserObjects.Token;
import com.example.healthyapp.Model.UserObjects.User;
import com.google.gson.annotations.SerializedName;

public class ActualizarContraResponse {
    public ActualizarContraResponse(Throwable exception) {
        this.message = exception.getMessage();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
    @SerializedName("data")
    private User data;

    public User getData() {
        return data;
    }
    public void setData(User data) {
        this.data = data;
    }



}
