package com.example.healthyapp.Response;

import com.example.healthyapp.Model.DeviceObjects.Configuration;
import com.example.healthyapp.Model.UserObjects.Token;
import com.google.gson.annotations.SerializedName;

public class HabitosconfigResponse {

    public HabitosconfigResponse(Throwable exception) {
        this.message = exception.getMessage();
    }
    private String message;
    public String getMessage() {
        return message;
    }

    @SerializedName("data")
    private HabitosconfigResponse.data data;
    public void setData(HabitosconfigResponse.data data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public class data {
        @SerializedName("configuracion")
        private Configuration configuracion;
        @SerializedName("token")
        private Token token;
    }
}
