package com.example.healthyapp.Model.DeviceObjects;

import java.io.Serializable;

public class Configuration implements Serializable {
    private Integer id;
    private Integer user_id;
    private String data;
    private Integer tipo_configuracion_id;

    public Configuration(Integer id, String data) {
        this.id=id;
        this.data=data;
    }

    public Integer getId() {
        return id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public String getData() {
        return data;
    }

    public Integer getTipo_configuracion_id() {
        return tipo_configuracion_id;
    }
}
