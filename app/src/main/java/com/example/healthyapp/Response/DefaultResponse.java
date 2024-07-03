package com.example.healthyapp.Response;

public class DefaultResponse<tipo> {
    public String getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    private String status;
    private String title;
    private String message;

    private tipo data;

    public tipo getData() {
        return data;
    }
}
