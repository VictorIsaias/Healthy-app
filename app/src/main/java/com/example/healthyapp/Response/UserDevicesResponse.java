package com.example.healthyapp.Response;
import com.example.healthyapp.Model.UserObjects.User;

public class UserDevicesResponse {

    private String message;
    private User data;

    public UserDevicesResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public User getData() {
        return data;
    }


}
