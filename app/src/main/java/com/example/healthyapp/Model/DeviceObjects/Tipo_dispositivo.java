package com.example.healthyapp.Model.DeviceObjects;

import java.io.Serializable;

public class Tipo_dispositivo  implements Serializable {
    private String name;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
