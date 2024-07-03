package com.example.healthyapp.Response;

import com.example.healthyapp.Model.DeviceObjects.Tipo_dispositivo;

import java.util.List;

public class DeviceTypeResponse {
    private String message;
    private String title;
    private String type;

    private List<Tipo_dispositivo> data;

    public String getMessage() {
        return message;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public List<Tipo_dispositivo> getData() {
        return data;
    }
}
